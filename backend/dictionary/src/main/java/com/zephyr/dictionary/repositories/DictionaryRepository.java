package com.zephyr.dictionary.repositories;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.domain.Dictionary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DictionaryRepository extends ReactiveMongoRepository<Dictionary, String> {

    // TODO: implement
    Mono<Dictionary> updateUsage(Keyword keyword);
}