package com.zephyr.location.services.impl;

import com.zephyr.commons.DataAccessUtils;
import com.zephyr.location.domain.Country;
import com.zephyr.location.repositories.CountryRepository;
import com.zephyr.location.services.CountryService;
import com.zephyr.location.services.ProxyService;
import com.zephyr.location.services.converters.LocalizingConverter;
import com.zephyr.location.services.dto.CountryDto;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Locale;

@Service
public class CountryServiceImpl implements CountryService {

    @Setter(onMethod = @__(@Autowired))
    private CountryRepository countryRepository;

    @Setter(onMethod = @__(@Autowired))
    private ProxyService proxyService;

    @Setter(onMethod = @__(@Autowired))
    private LocalizingConverter<Country, CountryDto> countryConverter;

    @Override
    public Mono<Page<CountryDto>> getSupportedCountries(Locale locale) {
        Flux<CountryDto> content = proxyService.findProxiesLocations()
                .transform(iso -> countryRepository.findById(iso))
                .map(c -> countryConverter.convert(c, locale));

        return DataAccessUtils.toReactivePage(content);
    }

    @Override
    public Mono<CountryDto> findByIsoCode(String iso, Locale locale) {
        return countryRepository.findOneByIso2(iso)
                .map(c -> countryConverter.convert(c, locale));
    }
}
