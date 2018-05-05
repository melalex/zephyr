package com.zephyr.keyword.services.provider.impl;

import com.zephyr.data.protocol.request.KeywordRequest;
import com.zephyr.data.protocol.dto.KeywordDto;
import com.zephyr.keyword.services.provider.KeywordsProvider;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class AdWordsKeywordsProvider implements KeywordsProvider {

    @Override
    public Flux<KeywordDto> provide(KeywordRequest request) {
        return Flux.empty();
    }
}
