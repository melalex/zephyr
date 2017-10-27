package com.zephyr.scraper.query.provider;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Task;

@FunctionalInterface
public interface QueryProvider {

    Request provide(Task task);
}
