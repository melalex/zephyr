package com.zephyr.location.business.services.impl;

import com.zephyr.commons.extensions.ExtendedMapper;
import com.zephyr.data.protocol.dto.CountryDto;
import com.zephyr.errors.utils.ExceptionUtils;
import com.zephyr.location.business.services.CountryService;
import com.zephyr.location.core.domain.Country;
import com.zephyr.location.core.repositories.CountryRepository;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {

    private static final int ZERO_DEPTH = 0;

    @Setter(onMethod = @__(@Autowired))
    private CountryRepository countryRepository;

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Override
    public CountryDto findByIso(String iso) {
        return countryRepository.findByIso(iso)
                .map(mapper.mapperFor(CountryDto.class))
                .orElseThrow(ExceptionUtils.newNotFoundError(Country.class, iso));
    }

    @Override
    public Set<CountryDto> findByNameStarts(String name) {
        return Optional.ofNullable(name)
                .map(countryRepository::findByNameStartingWith)
                .orElseGet(() -> countryRepository.findAllStream(ZERO_DEPTH))
                .map(mapper.mapperFor(CountryDto.class))
                .collect(Collectors.toSet());
    }
}
