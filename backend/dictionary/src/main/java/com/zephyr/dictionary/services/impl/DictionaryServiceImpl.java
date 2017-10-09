package com.zephyr.dictionary.services.impl;

import com.zephyr.commons.ReactorUtils;
import com.zephyr.commons.data.Keyword;
import com.zephyr.dictionary.domain.Dictionary;
import com.zephyr.dictionary.domain.factories.DictionaryFactory;
import com.zephyr.dictionary.integration.gateways.NewKeywordGateway;
import com.zephyr.dictionary.repositories.DictionaryRepository;
import com.zephyr.dictionary.services.DictionaryService;
import com.zephyr.dictionary.services.dto.DictionaryDto;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class DictionaryServiceImpl implements DictionaryService {

    @Setter(onMethod = @__(@Autowired))
    private DictionaryRepository dictionaryRepository;

    @Setter(onMethod = @__(@Autowired))
    private DictionaryFactory dictionaryFactory;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Setter(onMethod = @__(@Autowired))
    private NewKeywordGateway newKeywordGateway;

    @Override
    public Flux<DictionaryDto> update(Flux<Keyword> keywords) {
        return Flux.zip(keywords, isExist(keywords))
                .map(t -> updateKeyword(t.getT1(), t.getT2()))
                .transform(ReactorUtils.joiner())
                .map(mapper.mapperFor(DictionaryDto.class));
    }

    private Flux<Boolean> isExist(Flux<Keyword> keywords) {
        return keywords
                .map(dictionaryFactory::newDictionaryExample)
                .map(dictionaryRepository::exists)
                .transform(ReactorUtils.joiner());
    }

    private Mono<Dictionary> updateKeyword(Keyword keyword, Boolean isPresent) {
        if (isPresent) {
            log.info("Update usage of keyword: {}", keyword);
            return dictionaryRepository.updateUsage(keyword);
        } else {
            log.info("Save new keyword: {}", keyword);
            return dictionaryRepository
                    .save(dictionaryFactory.newDictionary(keyword))
                    .doOnSuccess(d -> newKeywordGateway.send(d.getKeyword()));
        }
    }
}
