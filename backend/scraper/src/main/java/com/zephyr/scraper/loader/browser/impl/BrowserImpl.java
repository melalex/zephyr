package com.zephyr.scraper.loader.browser.impl;

import com.zephyr.scraper.domain.*;
import com.zephyr.scraper.domain.exceptions.FraudException;
import com.zephyr.scraper.domain.exceptions.RequestException;
import com.zephyr.scraper.loader.agent.WebClientFactory;
import com.zephyr.scraper.loader.browser.Browser;
import com.zephyr.scraper.loader.fraud.FraudAnalyzer;
import com.zephyr.scraper.source.ProxySource;
import lombok.Setter;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
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

    @Value("${browser.retry.retryCount}")
    private int retryCount;

    @Setter(onMethod = @__(@Autowired))
    private WebClientFactory webClientFactory;

    @Setter(onMethod = @__(@Autowired))
    private FraudAnalyzer fraudAnalyzer;

    @Setter(onMethod = @__(@Autowired))
    private ProxySource proxySource;

    @Override
    public Mono<PageResponse> browse(Request request, PageRequest page) {
        return Mono.defer(() -> toRequestContext(request, page))
                .flatMap(this::makeRequest)
                .retryWhen(fraudException());
    }

    private Mono<RequestContext> toRequestContext(Request request, PageRequest page) {
        return proxySource.getOne(request.getProvider())
                .map(p -> createRequestContext(request, page, p));
    }

    private RequestContext createRequestContext(Request request, PageRequest page, Proxy proxy) {
        return RequestContext.builder()
                .task(request.getTask())
                .provider(request.getProvider())
                .baseUrl(request.getBaseUrl())
                .uri(request.getUri())
                .page(page)
                .proxy(proxy)
                .build();
    }

    private Mono<PageResponse> makeRequest(RequestContext context) {
        return webClientFactory.create(context).get()
                .uri(context.getUri(), context.getPage().getParams())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(fraudAnalyzer::analyze)
                .map(r -> PageResponse.of(r, context.getPage().getNumber()))
                .retryWhen(webClientException())
                .onErrorMap(t -> new RequestException(t, context));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> webClientException() {
        return Retry.anyOf(WebClientException.class)
                .retryMax(retryCount)
                .exponentialBackoff(Duration.ofMillis(firstBackoff), Duration.ofMillis(maxBackoff));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> fraudException() {
        return Retry.anyOf(FraudException.class, WebClientException.class)
                .doOnRetry(c -> reportProxy(c.exception()));
    }

    private void reportProxy(Throwable throwable) {
        if (throwable instanceof RequestException) {
            RequestContext context = ((RequestException) throwable).getFailedRequest();

            proxySource.report(context.getProxy(), context.getProvider());
        }
    }
}