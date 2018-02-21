package com.zephyr.keyword.services.provider.impl;

import com.zephyr.commons.templates.AbstractProvider;
import com.zephyr.data.protocol.criteria.KeywordCriteria;
import com.zephyr.data.protocol.vo.KeywordVo;
import com.zephyr.keyword.properties.KeywordSource;
import com.zephyr.keyword.services.provider.KeywordsProvider;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
public class AdWordsKeywordsProvider extends AbstractProvider<KeywordSource> implements KeywordsProvider {

    public AdWordsKeywordsProvider() {
        super(KeywordSource.AD_WORDS);
    }

    @Override
    public Flux<KeywordVo> provide(KeywordCriteria request) {
        return Flux.empty();
    }
}
