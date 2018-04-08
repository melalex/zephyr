package com.zephyr.location.business.services;

import com.zephyr.data.protocol.dto.CountryDto;

import java.util.Set;

public interface CountryService {

    CountryDto findByIso(String iso);

    Set<CountryDto> findByNameStarts(String name);
}
