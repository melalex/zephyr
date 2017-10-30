package com.zephyr.personalisation.services.impl;

import com.zephyr.data.dto.LanguageDto;
import com.zephyr.personalisation.repositories.LanguageRepository;
import com.zephyr.personalisation.services.LanguageService;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Setter(onMethod = @__(@Autowired))
    private LanguageRepository languageRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Flux<LanguageDto> findAll() {
        return languageRepository.findAll()
                .map(mapper.mapperFor(LanguageDto.class));
    }
}
