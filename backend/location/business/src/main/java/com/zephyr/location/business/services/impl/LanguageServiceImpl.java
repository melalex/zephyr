package com.zephyr.location.business.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.LanguageDto;
import com.zephyr.location.business.services.LanguageService;
import com.zephyr.location.core.repositories.LanguageRepository;
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
