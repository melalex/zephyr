package com.zephyr.dictionary.services;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.services.dto.DictionaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface DictionaryService {

    Mono<DictionaryDto> findById(String id);

    Mono<Page<DictionaryDto>> findByKeyword(Keyword keyword, Pageable pageable);

    Mono<Page<DictionaryDto>> findAll(Pageable pageable);

    Flux<DictionaryDto> update(Flux<Keyword> keywords);
}
