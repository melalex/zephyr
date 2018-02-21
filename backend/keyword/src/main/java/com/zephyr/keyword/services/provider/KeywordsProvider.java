package com.zephyr.keyword.services.provider;

import com.zephyr.commons.interfaces.Provider;
import com.zephyr.data.protocol.criteria.KeywordCriteria;
import com.zephyr.data.protocol.vo.KeywordVo;
import com.zephyr.keyword.properties.KeywordSource;
import reactor.core.publisher.Flux;

public interface KeywordsProvider extends Provider<KeywordSource> {

    Flux<KeywordVo> provide(KeywordCriteria request);
}
