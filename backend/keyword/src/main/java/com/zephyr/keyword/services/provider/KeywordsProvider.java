package com.zephyr.keyword.services.provider;

import com.zephyr.data.protocol.request.KeywordRequest;
import com.zephyr.data.protocol.dto.KeywordDto;
import reactor.core.publisher.Flux;

public interface KeywordsProvider {

    Flux<KeywordDto> provide(KeywordRequest request);
}
