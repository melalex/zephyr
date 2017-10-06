package com.zephyr.dictionary.repositories;

import com.zephyr.dictionary.domain.Dictionary;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface DictionaryRepository extends ReactiveMongoRepository<Dictionary, String>, CustomDictionaryRepository {

}