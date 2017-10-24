package com.zephyr.scraper.loader.agent.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.agent.WebClientFactory;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.ipc.netty.http.client.HttpClientOptions;

@Component
public class WebClientFactoryImpl implements WebClientFactory {
    private static final String DO_NOT_TRACK = "DNT";
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    private final ClientHttpConnector directConnector = new ReactorClientHttpConnector();

    @Setter(onMethod = @__(@Autowired))
    private ConfigurationManager configurationManager;

    @Override
    public WebClient create(Request request) {
        return WebClient.builder()
                .baseUrl(request.getBaseUrl())
                .clientConnector(connector(request.getProvider()))
                .defaultHeader(HttpHeaders.REFERER, request.getBaseUrl())
                .defaultHeader(HttpHeaders.USER_AGENT, request.getTask().getUserAgent())
                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, request.getTask().getLanguageIso())
                .defaultHeader(HttpHeaders.ACCEPT, ACCEPT)
                .defaultHeader(HttpHeaders.ACCEPT_ENCODING, ENCODING)
                .defaultHeader(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .defaultHeader(HttpHeaders.UPGRADE, TRUE)
                .defaultHeader(DO_NOT_TRACK, TRUE)
                .defaultHeader(UPGRADE_INSECURE_REQUESTS, TRUE)
                .build();
    }

    private ClientHttpConnector connector(SearchEngine engine) {
        if (configurationManager.configFor(engine).isUseProxy()) {
            return new ReactorClientHttpConnector(this::configConnector);
        }

        return directConnector;
    }

    private void configConnector(HttpClientOptions.Builder builder) {

    }
}