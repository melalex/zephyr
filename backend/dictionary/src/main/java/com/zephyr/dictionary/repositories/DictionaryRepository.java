package com.zephyr.dictionary.repositories;

import com.zephyr.dictionary.domain.Dictionary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface DictionaryRepository extends ReactiveMongoRepository<Dictionary, String>, ExtendedDictionaryRepository {

    Flux<Dictionary> findAll(Pageable pageable);

    Flux<Dictionary> findAll(Example<Dictionary> example, Pageable pageable);
}