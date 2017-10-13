package com.zephyr.location.services.converters.impl;

import com.zephyr.data.LanguageDto;
import com.zephyr.location.domain.Language;
import com.zephyr.location.services.converters.LocalizingConverter;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LanguageConverter implements LocalizingConverter<Language, LanguageDto> {

    @Override
    public LanguageDto convert(Language language, Locale locale) {
        LanguageDto result = new LanguageDto();

        result.setIso(language.getIso());
        result.setName(language.getName().getOrDefault(locale.getLanguage(), Locale.ENGLISH.getLanguage()));

        return result;
    }
}