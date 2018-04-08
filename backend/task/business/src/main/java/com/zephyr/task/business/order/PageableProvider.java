package com.zephyr.task.business.order;

import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface PageableProvider {

    Pageable provide(int batchSize);
}
