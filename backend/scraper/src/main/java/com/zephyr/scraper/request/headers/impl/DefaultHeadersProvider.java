package com.zephyr.scraper.request.headers.impl;

import com.zephyr.commons.support.MultiMapBuilder;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.agent.UserAgentProvider;
import com.zephyr.scraper.request.headers.HeadersProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class DefaultHeadersProvider implements HeadersProvider {

    private static final String DO_NOT_TRACK = "DNT";
    private static final String ENCODING = "gzip, deflate, br";
    private static final String KEEP_ALIVE = "keep-alive";
    private static final String TRUE = "1";

    @Setter(onMethod = @__(@Autowired))
    private UserAgentProvider userAgentProvider;

    @Override
    public Map<String, List<String>> provide(Query query, String baseUrl) {
        return MultiMapBuilder.create()
                .put(HttpHeaders.USER_AGENT, getUserAgent(query))
                .put(HttpHeaders.ACCEPT_LANGUAGE, query.getLanguageIso())
                .put(HttpHeaders.ACCEPT_ENCODING, ENCODING)
                .put(HttpHeaders.CONNECTION, KEEP_ALIVE)
                .put(DO_NOT_TRACK, TRUE)
                .build();
    }

    private String getUserAgent(Query query) {
        return Optional.ofNullable(query.getUserAgent())
                .orElseGet(userAgentProvider::provide)
                .getHeader();
    }
}
