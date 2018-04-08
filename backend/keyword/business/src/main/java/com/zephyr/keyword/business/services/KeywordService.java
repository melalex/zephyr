package com.zephyr.keyword.business.services;

import com.zephyr.data.protocol.criteria.KeywordCriteria;
import com.zephyr.data.protocol.vo.KeywordVo;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

public interface KeywordService {

    Flux<KeywordVo> findKeywords(@Valid KeywordCriteria request);
}
