package com.zephyr.location.services;

import com.zephyr.data.LanguageDto;
import reactor.core.publisher.Flux;

import java.util.Locale;

public interface LanguageService {

    Flux<LanguageDto> getSupportedLanguages(Locale locale);
}
