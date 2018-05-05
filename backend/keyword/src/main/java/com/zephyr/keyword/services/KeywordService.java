package com.zephyr.keyword.services;

import com.zephyr.data.protocol.request.KeywordRequest;
import com.zephyr.data.protocol.dto.KeywordDto;
import reactor.core.publisher.Flux;

import javax.validation.Valid;

public interface KeywordService {

    Flux<KeywordDto> findKeywords(@Valid KeywordRequest request);
}
