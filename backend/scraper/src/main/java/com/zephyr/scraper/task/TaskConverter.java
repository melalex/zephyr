package com.zephyr.scraper.task;

import com.zephyr.data.Keyword;
import com.zephyr.scraper.domain.Task;
import reactor.core.publisher.Mono;

public interface TaskConverter {

    Mono<Task> convert(Keyword keyword);
}
