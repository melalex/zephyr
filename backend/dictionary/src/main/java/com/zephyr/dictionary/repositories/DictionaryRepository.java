package com.zephyr.dictionary.repositories;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.domain.Dictionary;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DictionaryRepository extends ReactiveMongoRepository<Dictionary, String> {

    Mono<Dictionary> findByKeyword(Keyword keyword);

    // TODO: implement
    Mono<Void> updateUsage(Keyword keyword);
}