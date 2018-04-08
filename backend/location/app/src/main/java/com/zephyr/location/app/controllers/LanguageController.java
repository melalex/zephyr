package com.zephyr.location.app.controllers;

import com.zephyr.data.protocol.dto.LanguageDto;
import com.zephyr.location.business.services.LanguageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/v1/languages")
public class LanguageController {

    @Setter(onMethod = @__(@Autowired))
    private LanguageService languageService;

    @GetMapping
    @Cacheable("LANGUAGE_ALL")
    public Set<LanguageDto> findAll() {
        return languageService.findAll();
    }
}