package com.zephyr.location.repositories;

import com.zephyr.location.domain.Language;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface LanguageRepository extends ReactiveMongoRepository<Language, String> {

}