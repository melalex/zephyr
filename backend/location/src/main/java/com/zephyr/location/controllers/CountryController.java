package com.zephyr.location.controllers;

import com.zephyr.data.dto.CountryDto;
import com.zephyr.location.services.CountryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/v1/countries")
public class CountryController {

    @Setter(onMethod = @__(@Autowired))
    private CountryService countryService;

    @GetMapping("/{iso}")
    @Cacheable("COUNTRY_BY_ISO")
    public CountryDto findByIso(@PathVariable("iso") String iso) {
        return countryService.findByIso(iso);
    }

    @GetMapping
    @Cacheable("COUNTRY_BY_NAME")
    public Set<CountryDto> find(@RequestParam(required = false) String name) {
        return countryService.findByNameStarts(name);
    }
}