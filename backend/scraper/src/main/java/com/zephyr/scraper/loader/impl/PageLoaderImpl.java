package com.zephyr.scraper.loader.impl;

import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.loader.PageLoader;
import com.zephyr.scraper.loader.connector.ConnectorFactory;
import com.zephyr.scraper.source.UserAgentSource;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Component
@RefreshScope
public class PageLoaderImpl implements PageLoader {
    private static final String DO_NOT_TRACK = "DNT";
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    @Setter(onMethod = @__(@Autowired))
    private ConfigurationManager configurationManager;

    @Setter(onMethod = @__(@Autowired))
    private UserAgentSource userAgentSource;

    @Setter(onMethod = @__(@Autowired))
    private ConnectorFactory connectorFactory;

    @Override
    public Mono<Response> load(Request request) {
        WebClient webClient = createClient(request);

        return toFlux(request)
                .map(p -> webClient.get().uri(request.getUri(), p).retrieve())
                .flatMap(c -> c.bodyToMono(String.class))
                .collectList()
                .map(h -> Response.of(request.getTask(), request.getProvider(), h));
    }

    private Flux<Map<String, ?>> toFlux(Request request) {
        List<Map<String, ?>> pages = request.getPages();
        long delay = configurationManager.configFor(request.getProvider()).getDelay();

        Mono<Map<String, ?>> first = Mono.justOrEmpty(pages.stream().findFirst());
        Flux<Map<String, ?>> rest = Flux.fromStream(pages.stream().skip(1))
                .delayElements(Duration.ofMillis(delay));

        return Flux.concat(first, rest);
    }

    private WebClient createClient(Request request) {
        String country = request.getTask().getCountryIso();
        String language = request.getTask().getLanguageIso();
        boolean useProxy = configurationManager.configFor(request.getProvider()).isUseProxy();

        return WebClient.builder()
                .clientConnector(connectorFactory.create(country, useProxy))
                .baseUrl(request.getBaseUrl())
                .defaultHeader(HttpHeaders.USER_AGENT, userAgentSource.provide())
                .defaultHeader(HttpHeaders.ACCEPT, ACCEPT)
                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, language)
                .defaultHeader(HttpHeaders.ACCEPT_ENCODING, ENCODING)
                .defaultHeader(HttpHeaders.REFERER, request.getBaseUrl())
                .defaultHeader(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .defaultHeader(HttpHeaders.UPGRADE, TRUE)
                .defaultHeader(DO_NOT_TRACK, TRUE)
                .defaultHeader(UPGRADE_INSECURE_REQUESTS, TRUE)
                .build();
    }
}