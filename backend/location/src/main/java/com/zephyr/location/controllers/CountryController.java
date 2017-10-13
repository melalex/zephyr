package com.zephyr.location.controllers;

import com.zephyr.data.CountryDto;
import com.zephyr.location.services.CountryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
@RequestMapping("/v1/country")
public class CountryController {

    @Setter(onMethod = @__(@Autowired))
    private CountryService countryService;

    @GetMapping("/supports")
    public Flux<CountryDto> supportedCountries(Locale locale) {
        return countryService.getSupportedCountries(locale);
    }

    @GetMapping("/{iso}")
    public Mono<CountryDto> findByIsoCode(@PathVariable("iso") String iso, Locale locale) {
        return countryService.findByIsoCode(iso, locale);
    }
}