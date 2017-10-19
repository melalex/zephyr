package com.zephyr.location.services.impl;

import com.zephyr.data.dto.CountryDto;
import com.zephyr.errors.utils.ErrorUtil;
import com.zephyr.location.domain.Country;
import com.zephyr.location.repositories.CountryRepository;
import com.zephyr.location.services.CountryService;
import com.zephyr.mapping.mappers.ExtendedMapper;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CountryServiceImpl implements CountryService {

    @Setter(onMethod = @__(@Autowired))
    private CountryRepository countryRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public Mono<CountryDto> findByIso(String iso) {
        return countryRepository.findById(iso)
                .map(mapper.mapperFor(CountryDto.class))
                .switchIfEmpty(ErrorUtil.notFound(Country.class, iso));
    }

    @Override
    public Flux<CountryDto> findAll() {
        return countryRepository.findAll()
                .map(mapper.mapperFor(CountryDto.class));
    }

    @Override
    public Flux<CountryDto> findByNameStarts(String name) {
        return countryRepository.findAllByNameStartingWith(name)
                .map(mapper.mapperFor(CountryDto.class));
    }
}
