package com.zephyr.location.controllers;

import com.zephyr.data.protocol.dto.LanguageDto;
import com.zephyr.location.services.LanguageService;
import com.zephyr.location.util.Caches;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/v1/languages")
public class LanguageController {

    private LanguageService languageService;

    @GetMapping
    @Cacheable(Caches.LANGUAGE_ALL)
    public Set<LanguageDto> findAll() {
        return languageService.findAll();
    }
}