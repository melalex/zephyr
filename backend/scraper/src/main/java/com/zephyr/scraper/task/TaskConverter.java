package com.zephyr.scraper.task;

import com.zephyr.data.commons.Keyword;
import com.zephyr.scraper.domain.ScraperTask;
import reactor.core.publisher.Mono;

public interface TaskConverter {

    Mono<ScraperTask> convert(Keyword keyword);
}
