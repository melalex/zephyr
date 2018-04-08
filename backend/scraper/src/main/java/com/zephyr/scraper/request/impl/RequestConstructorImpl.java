package com.zephyr.scraper.request.impl;

import com.zephyr.commons.LoggingUtils;
import com.zephyr.scraper.domain.EngineRequest;
import com.zephyr.scraper.domain.Query;
import com.zephyr.scraper.request.RequestConstructor;
import com.zephyr.scraper.request.provider.RequestProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class RequestConstructorImpl implements RequestConstructor {

    private static final String NEW_REQUEST_MESSAGE = "Constructed Request with id '{}'";

    private List<RequestProvider> providers;

    @Override
    public Flux<EngineRequest> construct(Query query) {
        return Flux.fromIterable(providers)
                .map(RequestProvider.from(query))
                .flatMap(Flux::fromIterable)
                .doOnNext(LoggingUtils.info(log, EngineRequest::getId, NEW_REQUEST_MESSAGE));
    }
}