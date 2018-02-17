package com.zephyr.location.services;

import com.zephyr.data.protocol.dto.LanguageDto;

import java.util.Set;

public interface LanguageService {

    Set<LanguageDto> findAll();
}