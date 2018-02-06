package com.zephyr.keyword.services;

import com.zephyr.keyword.protocol.KeywordRequest;
import com.zephyr.keyword.protocol.KeywordResponse;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

public interface KeywordService {

    Flux<KeywordResponse> findKeywords(@Valid KeywordRequest request);
}
