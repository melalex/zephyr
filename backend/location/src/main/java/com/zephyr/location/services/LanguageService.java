package com.zephyr.location.services;

import com.zephyr.location.services.dto.LanguageDto;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.Locale;

public interface LanguageService {

    Mono<Page<LanguageDto>> getSupportedLanguages(Locale locale);
}
