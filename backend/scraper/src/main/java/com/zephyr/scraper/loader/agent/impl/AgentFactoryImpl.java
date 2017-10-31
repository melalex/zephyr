package com.zephyr.scraper.loader.agent.impl;

import com.zephyr.data.dto.ProxyDto;
import com.zephyr.scraper.loader.agent.AgentFactory;
import com.zephyr.scraper.loader.context.model.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.ipc.netty.http.client.HttpClientOptions;

import java.util.Objects;

@Slf4j
@Component
public class AgentFactoryImpl implements AgentFactory {
    private static final String DO_NOT_TRACK = "DNT";
    private static final String UPGRADE_INSECURE_REQUESTS = "Upgrade-Insecure-Requests";
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    private final ClientHttpConnector directConnector = new ReactorClientHttpConnector();

    @Override
    public WebClient create(RequestContext context) {
        log.info("Creating Agent for TaskDto {} and Engine {} on {} page",
                context.getTask().getId(), context.getProvider(), context.getPage().getNumber());

        return WebClient.builder()
                .baseUrl(context.getBaseUrl())
                .clientConnector(connector(context.getProxy()))
                .defaultHeader(HttpHeaders.REFERER, context.getBaseUrl())
                .defaultHeader(HttpHeaders.USER_AGENT, context.getTask().getUserAgent())
                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, context.getTask().getLanguageIso())
                .defaultHeader(HttpHeaders.ACCEPT, ACCEPT)
                .defaultHeader(HttpHeaders.ACCEPT_ENCODING, ENCODING)
                .defaultHeader(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .defaultHeader(HttpHeaders.UPGRADE, TRUE)
                .defaultHeader(DO_NOT_TRACK, TRUE)
                .defaultHeader(UPGRADE_INSECURE_REQUESTS, TRUE)
                .build();
    }

    private ClientHttpConnector connector(ProxyDto proxy) {
        if (Objects.nonNull(proxy)) {
            return new ReactorClientHttpConnector(this::configConnector);
        }

        return directConnector;
    }

    private void configConnector(HttpClientOptions.Builder builder) {

    }
}