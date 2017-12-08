package com.zephyr.location.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.dto.LanguageDto;
import com.zephyr.location.repositories.LanguageRepository;
import com.zephyr.location.services.LanguageService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Setter(onMethod = @__(@Autowired))
    private LanguageRepository languageRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Set<LanguageDto> findAll() {
        return languageRepository.findAllStream()
                .map(mapper.mapperFor(LanguageDto.class))
                .collect(Collectors.toSet());
    }
}
