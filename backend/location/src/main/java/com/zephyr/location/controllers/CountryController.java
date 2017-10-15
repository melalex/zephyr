package com.zephyr.location.controllers;

import com.zephyr.data.resources.CountryResource;
import com.zephyr.location.services.CountryService;
import com.zephyr.location.services.dto.CountryDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
@RequestMapping("/v1/country")
@ExposesResourceFor(CountryResource.class)
public class CountryController {

    @Setter(onMethod = @__(@Autowired))
    private CountryService countryService;

    @Setter(onMethod = @__(@Autowired))
    private ResourceAssembler<CountryDto, CountryResource> countryAssembler;

    @GetMapping("/supports")
    public Mono<PagedResources<CountryResource>> supportedCountries(
            PagedResourcesAssembler<CountryDto> pageAssembler,
            @RequestParam(value = "len", defaultValue = "en") String language
    ) {
        return countryService.getSupportedCountries(new Locale(language))
                .map(d -> pageAssembler.toResource(d, countryAssembler));
    }

    @GetMapping("/{iso}")
    public Mono<CountryResource> findByIsoCode(
            @PathVariable("iso") String iso,
            @RequestParam(value = "len", defaultValue = "en") String language
    ) {
        return countryService.findByIsoCode(iso, new Locale(language))
                .map(d -> countryAssembler.toResource(d));
    }
}