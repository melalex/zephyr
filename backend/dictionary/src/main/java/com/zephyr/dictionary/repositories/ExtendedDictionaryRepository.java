package com.zephyr.dictionary.repositories;

import com.zephyr.data.Keyword;
import com.zephyr.dictionary.domain.Dictionary;
import org.joda.time.ReadablePeriod;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ExtendedDictionaryRepository {

    Mono<Dictionary> updateUsage(Keyword keyword);

    Flux<Dictionary> findAllForUpdate(ReadablePeriod relevancePeriod, Pageable pageable);
}