package com.zephyr.location.services.impl;

import com.zephyr.data.LanguageDto;
import com.zephyr.location.domain.Language;
import com.zephyr.location.repositories.LanguageRepository;
import com.zephyr.location.services.LanguageService;
import com.zephyr.location.services.converters.LocalizingConverter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.util.Locale;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Setter(onMethod = @__(@Autowired))
    private LanguageRepository languageRepository;

    @Setter(onMethod = @__(@Autowired))
    private LocalizingConverter<Language, LanguageDto> languageConverter;

    @Override
    public Flux<LanguageDto> getSupportedLanguages(Locale locale) {
        return languageRepository.findAll()
                .map(l -> languageConverter.convert(l, locale));
    }
}