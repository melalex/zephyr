package com.zephyr.location.controllers;

import com.zephyr.data.resources.LanguageResource;
import com.zephyr.location.services.LanguageService;
import com.zephyr.location.services.dto.LanguageDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Locale;

@RestController
@RequestMapping("/v1/language")
@ExposesResourceFor(LanguageResource.class)
public class LanguageController {

    @Setter(onMethod = @__(@Autowired))
    private LanguageService languageService;

    @Setter
    private ResourceAssembler<LanguageDto, LanguageResource> languageAssembler;

    @RequestMapping("/supports")
    public Mono<PagedResources<LanguageResource>> supportedLanguages(
            PagedResourcesAssembler<LanguageDto> pageAssembler,
            @RequestParam(value = "len", defaultValue = "en") String language
    ) {
        return languageService.getSupportedLanguages(new Locale(language))
                .map(d -> pageAssembler.toResource(d, languageAssembler));
    }
}