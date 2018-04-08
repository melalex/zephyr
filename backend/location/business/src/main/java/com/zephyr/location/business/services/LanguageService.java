package com.zephyr.location.business.services;

import com.zephyr.data.protocol.dto.LanguageDto;

import java.util.Set;

public interface LanguageService {

    Set<LanguageDto> findAll();
}