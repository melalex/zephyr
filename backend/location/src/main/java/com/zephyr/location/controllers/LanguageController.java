package com.zephyr.location.controllers;

import com.zephyr.data.LanguageDto;
import com.zephyr.location.services.LanguageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Locale;

@RestController
@RequestMapping("/v1/language")
public class LanguageController {

    @Setter(onMethod = @__(@Autowired))
    private LanguageService languageService;

    @RequestMapping("/supports")
    public Flux<LanguageDto> supportedLanguages(Locale locale) {
        return languageService.getSupportedLanguages(locale);
    }
}