package com.zephyr.dictionary.services;

import com.zephyr.commons.data.Keyword;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DictionaryService {

    Mono<Void> update(Flux<Keyword> keywords);

    Flux<Keyword> findAll(Pageable pageable);
}
