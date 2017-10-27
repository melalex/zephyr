package com.zephyr.scraper.loader.browser.impl;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.PageResponse;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.agent.AgentFactory;
import com.zephyr.scraper.loader.browser.Browser;
import com.zephyr.scraper.loader.context.ContextManager;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.loader.exceptions.RequestException;
import com.zephyr.scraper.loader.fraud.FraudAnalyzer;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.retry.Retry;

import java.time.Duration;
import java.util.function.Function;

@Component
@RefreshScope
public class BrowserImpl implements Browser {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private AgentFactory agentFactory;

    @Setter(onMethod = @__(@Autowired))
    private FraudAnalyzer fraudAnalyzer;

    @Setter(onMethod = @__(@Autowired))
    private ContextManager contextManager;

    @Override
    public Mono<PageResponse> browse(Request request, PageRequest page) {
        return Mono.defer(() -> contextManager.toContext(request, page))
                .flatMap(this::makeRequest)
                .retryWhen(requestException());
    }

    private Mono<PageResponse> makeRequest(RequestContext context) {
        return agentFactory.create(context).get()
                .uri(context.getUri(), context.getPage().getParams())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> fraudAnalyzer.analyze(response, context.getProvider()))
                .map(r -> PageResponse.of(r, context.getPage().getNumber()))
                .retryWhen(webClientException())
                .onErrorMap(t -> new RequestException(t, context));
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> webClientException() {
        return Retry.anyOf(WebClientException.class)
                .retryMax(scraperProperties.getBrowser().getRetryCount())
                .exponentialBackoff(
                        Duration.ofMillis(scraperProperties.getBrowser().getFirstBackoff()),
                        Duration.ofMillis(scraperProperties.getBrowser().getMaxBackoff())
                );
    }

    private Function<Flux<Throwable>, ? extends Publisher<?>> requestException() {
        return Retry.anyOf(RequestException.class)
                .doOnRetry(c -> report(c.exception()));
    }

    private void report(Throwable throwable) {
        if (throwable instanceof RequestException) {
            contextManager.report(((RequestException) throwable).getFailedRequest());
        }
    }
}