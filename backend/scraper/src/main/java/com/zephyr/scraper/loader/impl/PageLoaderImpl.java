package com.zephyr.scraper.loader.impl;

import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.loader.PageLoader;
import com.zephyr.scraper.source.UserAgentSource;
import com.zephyr.scraper.loader.connector.ConnectorFactory;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;

@Component
@RefreshScope
public class PageLoaderImpl implements PageLoader {
    private static final String ROOT = "/";
    private static final String DO_NOT_TRACK = "DNT";
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String UTF8 = "UTF-8";
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
        String country = request.getKeyword().getCountryIso();
        String language = request.getKeyword().getLanguageIso();
        String word = request.getKeyword().getWord();
        boolean useProxy = configurationManager.configFor(request.getProvider()).isUseProxy();

        // @formatter:off
        return WebClient.builder()
                    .clientConnector(connectorFactory.create(country, useProxy))
                    .baseUrl(request.getUrl())
                .build()
                .get()
                    .uri(ROOT, request.getParams())
                    .header(HttpHeaders.USER_AGENT, userAgentSource.provide())
                    .header(HttpHeaders.ACCEPT, ACCEPT)
                    .header(HttpHeaders.ACCEPT_LANGUAGE, language)
                    .header(HttpHeaders.ACCEPT_ENCODING, ENCODING)
                    .header(HttpHeaders.REFERER, getReferer(word))
                    .header(HttpHeaders.CONNECTION, KEEP_ALIVE)
                    .header(HttpHeaders.UPGRADE, TRUE)
                    .header(DO_NOT_TRACK, TRUE)
                    .header(UPGRADE_INSECURE_REQUESTS, TRUE)
                .retrieve()
                .bodyToMono(String.class)
                .map(h -> Response.of(request.getKeyword(), request.getProvider(), h));
        // @formatter:on
    }

    @SneakyThrows
    private String getReferer(String word) {
        return "http://lmgtfy.com/?q=" + URLEncoder.encode(word, UTF8);
    }
}