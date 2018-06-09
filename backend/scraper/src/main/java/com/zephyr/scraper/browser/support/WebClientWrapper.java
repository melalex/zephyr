package com.zephyr.scraper.browser.support;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;

@Component
@AllArgsConstructor
public class WebClientWrapper {

    private WebClient webClient;

    public Mono<ClientResponse> get(String path, Map<String, List<String>> params, Map<String, List<String>> headers) {
        return webClient.get()
                .uri(path, params)
                .headers(h -> h.putAll(headers))
                .exchange();
    }
}
