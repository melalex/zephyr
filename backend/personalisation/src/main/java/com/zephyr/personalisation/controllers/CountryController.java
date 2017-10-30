package com.zephyr.personalisation.controllers;

import com.zephyr.data.dto.CountryDto;
import com.zephyr.personalisation.services.CountryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/country")
public class CountryController {

    @Setter(onMethod = @__(@Autowired))
    private CountryService countryService;

    @GetMapping("/{iso}")
    public Mono<CountryDto> findByIso(@PathVariable("iso") String iso) {
        return countryService.findByIso(iso);
    }

    @GetMapping("/")
    public Flux<CountryDto> findAll() {
        return countryService.findAll();
    }

    @GetMapping("/search")
    public Flux<CountryDto> findByNameStarts(String name) {
        return countryService.findByNameStarts(name);
    }
}