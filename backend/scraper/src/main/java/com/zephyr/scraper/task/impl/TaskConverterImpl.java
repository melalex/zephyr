package com.zephyr.scraper.task.impl;

import com.zephyr.data.Keyword;
import com.zephyr.scraper.domain.Task;
import com.zephyr.scraper.task.TaskConverter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class TaskConverterImpl implements TaskConverter {

    @Override
    public Mono<Task> convert(Keyword keyword) {
        return null;
    }
}