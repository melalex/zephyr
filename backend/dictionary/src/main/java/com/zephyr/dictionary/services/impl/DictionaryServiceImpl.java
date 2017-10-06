package com.zephyr.dictionary.services.impl;

import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.domain.Dictionary;
import com.zephyr.dictionary.domain.factories.DictionaryFactory;
import com.zephyr.dictionary.repositories.DictionaryRepository;
import com.zephyr.dictionary.services.DictionaryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Setter(onMethod = @__(@Autowired))
    private DictionaryRepository dictionaryRepository;

    @Setter(onMethod = @__(@Autowired))
    private DictionaryFactory dictionaryFactory;

    @Override
    public Mono<Void> update(Flux<Keyword> keywords) {
        return Flux.zip(keywords, isExist(keywords))
                .doOnNext(t -> updateKeyword(t.getT1(), t.getT2()))
                .then();
    }

    @Override
    public Flux<Keyword> findAll(Pageable pageable) {
        return null;
    }

    private Flux<Boolean> isExist(Flux<Keyword> keywords) {
        return Flux.merge(
                keywords
                        .map(dictionaryFactory::newDictionaryExample)
                        .map(dictionaryRepository::exists)
                        .toIterable()
        );
    }

    private Mono<Void> updateKeyword(Keyword keyword, Boolean isPresent) {
        if (isPresent) {
            return dictionaryRepository.updateUsage(keyword);
        } else {
            return dictionaryRepository
                    .save(dictionaryFactory.newDictionary(keyword))
                    .then();
        }
    }
}
