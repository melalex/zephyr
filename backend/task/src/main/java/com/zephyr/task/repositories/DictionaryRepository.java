package com.zephyr.task.repositories;

import com.zephyr.task.domain.Dictionary;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface DictionaryRepository extends ReactiveMongoRepository<Dictionary, String>, DictionaryOperations {

    Flux<Dictionary> findAll(Pageable pageable);

    Flux<Dictionary> findAll(Example<Dictionary> example, Pageable pageable);
}