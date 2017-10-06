package com.zephyr.dictionary.repositories;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.domain.Dictionary;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomDictionaryRepository {

    Mono<Dictionary> updateUsage(Keyword keyword);

    Flux<Dictionary> findAllForUpdate();
}
