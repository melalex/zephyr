package com.zephyr.scraper.task.impl;

import com.zephyr.data.Keyword;
import com.zephyr.mapping.mappers.ExtendedMapper;
import com.zephyr.scraper.domain.Task;
import com.zephyr.scraper.source.LocationSource;
import com.zephyr.scraper.task.TaskConverter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TaskConverterImpl implements TaskConverter {

    @Setter(onMethod = @__(@Autowired))
    private ExtendedMapper mapper;

    @Setter(onMethod = @__(@Autowired))
    private LocationSource locationSource;

    @Override
    public Mono<Task> convert(Keyword keyword) {
        return Mono.just(keyword)
                .map(mapper.mapperFor(Task.class))
                .flatMap(this::populateCountryData)
                .flatMap(this::populateLocationData);
    }

    private Mono<Task> populateCountryData(Task task) {
        return locationSource.findCountry(task.getCountryIso())
                .map(c -> {
                    task.setLocaleGoogle(c.getLocaleGoogle());
                    task.setLocaleYandex(c.getLocaleYandex());
                    return task;
                });
    }

    private Mono<Task> populateLocationData(Task task) {
        return locationSource.findPlace(task.getCountryIso(), task.getPlace())
                .map(p -> {
                    task.setParent(p.getParent());
                    task.setLocation(p.getLocation());
                    return task;
                });
    }
}