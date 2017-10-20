package com.zephyr.scraper.loader.browser.impl;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.loader.browser.Browser;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@AllArgsConstructor(staticName = "of")
public class BrowserImpl implements Browser {
    private WebClient client;
    private String uri;

    @Override
    public Mono<Response.PageResponse> browse(Request.PageRequest request) {
        return client.get()
                .uri(uri, request.getParams())
                .retrieve()
                .bodyToMono(String.class)
                .map(r -> Response.PageResponse.of(r, request.getNumber()));
    }
}