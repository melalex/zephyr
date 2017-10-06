package com.zephyr.dictionary.repositories.impl;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.domain.Dictionary;
import com.zephyr.dictionary.repositories.CustomDictionaryRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class CustomDictionaryRepositoryImpl implements CustomDictionaryRepository {

    @Setter(onMethod = @__(@Autowired))
    private MongoOperations mongoOperations;

    @Override
    public Mono<Dictionary> updateUsage(Keyword keyword) {
        return null;
    }

    @Override
    public Flux<Dictionary> findAllForUpdate() {
        return null;
    }
}
