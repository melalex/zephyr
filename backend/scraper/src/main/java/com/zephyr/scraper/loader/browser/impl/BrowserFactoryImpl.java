package com.zephyr.scraper.loader.browser.impl;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.browser.Browser;
import com.zephyr.scraper.loader.browser.BrowserFactory;
import com.zephyr.scraper.loader.connector.ConnectorFactory;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BrowserFactoryImpl implements BrowserFactory {
    private static final String DO_NOT_TRACK = "DNT";
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    @Setter(onMethod = @__(@Autowired))
    private ConnectorFactory connectorFactory;

    @Override
    public Browser create(Request request) {
        return BrowserImpl.of(createClient(request), request.getUri());
    }

    private WebClient createClient(Request request) {
        return WebClient.builder()
                .baseUrl(request.getBaseUrl())
                .clientConnector(connectorFactory.create(request.getProvider()))
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
}