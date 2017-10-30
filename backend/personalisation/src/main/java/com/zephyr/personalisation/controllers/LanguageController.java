package com.zephyr.personalisation.controllers;

import com.zephyr.data.dto.LanguageDto;
import com.zephyr.personalisation.services.LanguageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/v1/language")
public class LanguageController {

    @Setter(onMethod = @__(@Autowired))
    private LanguageService languageService;

    @GetMapping("/")
    public Flux<LanguageDto> findAll() {
        return languageService.findAll();
    }
}