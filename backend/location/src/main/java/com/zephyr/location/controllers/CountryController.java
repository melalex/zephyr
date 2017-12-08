package com.zephyr.location.controllers;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.zephyr.data.dto.CountryDto;
import com.zephyr.location.services.CountryService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/country")
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
    public Set<MappingJacksonValue> find(@RequestParam(required = false) String name, Set<String> fields) {
        return countryService.findByNameStarts(name)
                .stream()
                .map(MappingJacksonValue::new)
                .peek(v -> v.setFilters(getFilters(fields)))
                .collect(Collectors.toSet());
    }

    private FilterProvider getFilters(Set<String> fields) {
        return null;
    }
}