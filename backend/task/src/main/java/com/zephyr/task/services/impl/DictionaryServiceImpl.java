package com.zephyr.task.services.impl;

import com.zephyr.commons.DataAccessUtils;
import com.zephyr.data.commons.Keyword;
import com.zephyr.task.domain.Dictionary;
import com.zephyr.task.domain.factories.DictionaryFactory;
import com.zephyr.task.integration.gateways.NewKeywordGateway;
import com.zephyr.task.repositories.DictionaryRepository;
import com.zephyr.task.services.DictionaryService;
import com.zephyr.task.services.dto.DictionaryDto;
import com.zephyr.errors.utils.ErrorUtil;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Mono<DictionaryDto> findById(String id) {
        return dictionaryRepository.findById(id)
                .map(mapper.mapperFor(DictionaryDto.class))
                .switchIfEmpty(ErrorUtil.notFound(Dictionary.class, id));
    }

    @Override
    public Mono<Page<DictionaryDto>> findByKeyword(Keyword keyword, Pageable pageable) {
        Flux<DictionaryDto> content = dictionaryRepository
                .findAll(dictionaryFactory.newDictionaryExample(keyword), pageable)
                .map(mapper.mapperFor(DictionaryDto.class));

        Mono<Long> count = dictionaryRepository.count();

        return DataAccessUtils.toReactivePage(content, count, pageable);
    }

    @Override
    public Mono<Page<DictionaryDto>> findAll(Pageable pageable) {
        Flux<DictionaryDto> content = dictionaryRepository.findAll(pageable)
                .map(mapper.mapperFor(DictionaryDto.class));
        Mono<Long> count = dictionaryRepository.count();

        return DataAccessUtils.toReactivePage(content, count, pageable);
    }

    @Override
    public Flux<DictionaryDto> update(Flux<Keyword> keywords) {
        return Flux.zip(keywords, isExist(keywords))
                .flatMap(t -> updateKeyword(t.getT1(), t.getT2()))
                .map(mapper.mapperFor(DictionaryDto.class));
    }

    private Flux<Boolean> isExist(Flux<Keyword> keywords) {
        return keywords
                .map(dictionaryFactory::newDictionaryExample)
                .flatMap(dictionaryRepository::exists);
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
