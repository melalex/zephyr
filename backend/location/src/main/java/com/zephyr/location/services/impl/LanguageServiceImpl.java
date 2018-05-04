package com.zephyr.location.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.LanguageDto;
import com.zephyr.location.repositories.LanguageRepository;
import com.zephyr.location.services.LanguageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LanguageServiceImpl implements LanguageService {

    private LanguageRepository languageRepository;
    private ExtendedMapper mapper;

    @Override
    public Set<LanguageDto> findAll() {
        return languageRepository.findAllStream()
                .map(mapper.mapperFor(LanguageDto.class))
                .collect(Collectors.toSet());
    }
}
