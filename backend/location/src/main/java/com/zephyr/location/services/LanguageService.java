package com.zephyr.location.services;

import com.zephyr.data.dto.LanguageDto;
import reactor.core.publisher.Flux;

public interface LanguageService {

    Flux<LanguageDto> findAll();
}