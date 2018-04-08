package com.zephyr.keyword.business.services.provider.impl;

import com.zephyr.data.protocol.criteria.KeywordCriteria;
import com.zephyr.data.protocol.vo.KeywordVo;
import com.zephyr.keyword.business.services.provider.KeywordsProvider;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class AdWordsKeywordsProvider implements KeywordsProvider {

    @Override
    public Flux<KeywordVo> provide(KeywordCriteria request) {
        return Flux.empty();
    }
}
