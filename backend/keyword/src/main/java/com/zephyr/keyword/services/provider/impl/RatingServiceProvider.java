package com.zephyr.keyword.services.provider.impl;

import com.zephyr.keyword.protocol.KeywordRequest;
import com.zephyr.keyword.protocol.KeywordResponse;
import com.zephyr.keyword.services.provider.KeywordsProvider;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class RatingServiceProvider implements KeywordsProvider {

    @Override
    public Flux<KeywordResponse> provide(KeywordRequest request) {
        return Flux.empty();
    }
}
