package com.zephyr.task.services.order;

import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface PageableProvider {

    Pageable provide(int batchSize);
}
