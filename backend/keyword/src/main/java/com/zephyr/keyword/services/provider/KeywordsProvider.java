package com.zephyr.keyword.services.provider;

import com.zephyr.keyword.protocol.KeywordRequest;
import com.zephyr.keyword.protocol.KeywordResponse;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface KeywordsProvider {

    Flux<KeywordResponse> provide(KeywordRequest request);
}
