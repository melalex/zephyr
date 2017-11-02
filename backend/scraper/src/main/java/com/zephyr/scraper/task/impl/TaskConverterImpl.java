package com.zephyr.scraper.task.impl;

import com.zephyr.data.commons.Keyword;
import com.zephyr.mapping.mappers.ExtendedMapper;
import com.zephyr.scraper.domain.ScraperTask;
import com.zephyr.scraper.task.location.LocationSource;
import com.zephyr.scraper.task.TaskConverter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class TaskConverterImpl implements TaskConverter {

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Setter(onMethod = @__(@Autowired))
    private LocationSource locationSource;

    @Override
    public Mono<ScraperTask> convert(Keyword keyword) {
        ScraperTask task = mapper.map(keyword, ScraperTask.class);
        task.setId(UUID.randomUUID().toString());

        return locationSource.findCountry(task.getCountryIso())
                .doOnNext(c -> task.setLocaleGoogle(c.getLocaleGoogle()))
                .doOnNext(c -> task.setLocaleYandex(c.getLocaleYandex()))
                .then(locationSource.findPlace(task.getCountryIso(), task.getPlace()))
                .doOnNext(p -> task.setParent(p.getParent()))
                .doOnNext(p -> task.setLocation(p.getLocation()))
                .then(Mono.just(task));
    }
}