package com.zephyr.location.services;

import com.zephyr.location.services.dto.CountryDto;
import org.springframework.data.domain.Page;
import reactor.core.publisher.Mono;

import java.util.Locale;

public interface CountryService {

    Mono<Page<CountryDto>> getSupportedCountries(Locale locale);

    Mono<CountryDto> findByIsoCode(String iso, Locale locale);
}
