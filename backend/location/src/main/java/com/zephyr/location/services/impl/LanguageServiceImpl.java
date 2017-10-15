package com.zephyr.location.services.impl;

import com.zephyr.commons.DataAccessUtils;
import com.zephyr.location.domain.Language;
import com.zephyr.location.repositories.LanguageRepository;
import com.zephyr.location.services.LanguageService;
import com.zephyr.location.services.converters.LocalizingConverter;
import com.zephyr.location.services.dto.LanguageDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Setter(onMethod = @__(@Autowired))
    private LanguageRepository languageRepository;

    @Setter(onMethod = @__(@Autowired))
    private LocalizingConverter<Language, LanguageDto> languageConverter;

    @Override
    public Mono<Page<LanguageDto>> getSupportedLanguages(Locale locale) {
        Flux<LanguageDto> content = languageRepository.findAll()
                .map(l -> languageConverter.convert(l, locale));

        Mono<Long> count = languageRepository.count();

        return DataAccessUtils.toReactivePage(content, count);
    }
}