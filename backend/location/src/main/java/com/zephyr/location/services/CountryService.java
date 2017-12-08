package com.zephyr.location.services;

import com.zephyr.data.dto.CountryDto;

import java.util.Set;

public interface CountryService {

    CountryDto findByIso(String iso);

    Set<CountryDto> findByNameStarts(String name);
}
