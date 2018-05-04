package com.zephyr.location.controllers;

import com.zephyr.data.protocol.dto.CountryDto;
import com.zephyr.location.services.CountryService;
import com.zephyr.location.util.Caches;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/countries")
public class CountryController {

    private CountryService countryService;

    @GetMapping("/{iso}")
    @Cacheable(Caches.COUNTRY_BY_ISO)
    public CountryDto findByIso(@PathVariable String iso) {
        return countryService.findByIso(iso);
    }

    @GetMapping
    @Cacheable(Caches.COUNTRY_BY_NAME)
    public Set<CountryDto> find(@RequestParam(required = false) String name) {
        return countryService.findByNameStarts(name);
    }
}