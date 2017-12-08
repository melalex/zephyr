package com.zephyr.location.services;

import com.zephyr.data.dto.LanguageDto;

import java.util.Set;

public interface LanguageService {

    Set<LanguageDto> findAll();
}