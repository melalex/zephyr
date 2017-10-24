package com.zephyr.scraper.loader.browser.impl;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.agent.WebClientFactory;
import com.zephyr.scraper.loader.browser.Browser;
import lombok.Setter;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import java.time.Duration;
import java.util.function.Function;

@Component
public class BrowserImpl implements Browser {

    @Value("${browser.retry.firstBackoff}")
    private long firstBackoff;

    @Value("${browser.retry.maxBackoff}")
    private long maxBackoff;

    @Setter(onMethod = @__(@Autowired))
    private WebClientFactory webClientFactory;

    @Override
    public Mono<PageResponse> browse(Request request, PageRequest page) {
        return Mono.fromSupplier(() -> webClientFactory.create(request))
                .flatMap(c -> makeRequest(c, request.getUri(), page));
    }

    private Mono<PageResponse> makeRequest(WebClient client, String uri, PageRequest request) {
        return client.get()
                .uri(uri, request.getParams())
                .retrieve()
                .bodyToMono(String.class)
                .map(r -> PageResponse.of(r, request.getNumber()))
                .retryWhen(retryStrategy());
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> retryStrategy() {
        return Retry.anyOf(WebClientException.class)
                .exponentialBackoff(Duration.ofMillis(firstBackoff), Duration.ofMillis(maxBackoff));
    }
}