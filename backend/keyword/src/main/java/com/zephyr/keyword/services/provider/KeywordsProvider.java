package com.zephyr.keyword.services.provider;

import com.zephyr.data.protocol.criteria.KeywordCriteria;
import com.zephyr.data.protocol.vo.KeywordVo;
import reactor.core.publisher.Flux;

@FunctionalInterface
public interface KeywordsProvider {

    Flux<KeywordVo> provide(KeywordCriteria request);
}
