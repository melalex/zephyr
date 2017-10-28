package com.zephyr.scraper.task.location.impl;

import com.google.common.collect.ImmutableMap;
import com.zephyr.data.dto.CountryDto;
import com.zephyr.data.dto.PlaceDto;
import com.zephyr.scraper.clients.LocationServiceClient;
import com.zephyr.scraper.task.location.LocationSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class LocationSourceImpl implements LocationSource {
    private Mono<ImmutableMap<String, CountryDto>> countryCache;

    @Setter(onMethod = @__(@Autowired))
    private LocationServiceClient locationServiceClient;

    @PostConstruct
    public void init() {
        countryCache = locationServiceClient.findAll()
                .reduce(new ConcurrentHashMap<String, CountryDto>(), (m, c) -> {
                    m.put(c.getIso(), c);
                    return m;
                })
                .map(ImmutableMap::copyOf)
                .doOnNext(c -> log.info("Finished countryCache loading"))
                .cache();
    }

    @Override
    public Mono<CountryDto> findCountry(String iso) {
        return countryCache.map(m -> m.get(iso));
    }

    @Override
    public Mono<PlaceDto> findPlace(String iso, String name) {
        return locationServiceClient.findByCountryIsoAndName(iso, name)
                .doOnNext(p -> log.info("Received place: {}", p));
    }
}