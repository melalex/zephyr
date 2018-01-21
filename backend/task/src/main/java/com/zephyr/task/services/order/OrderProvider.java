package com.zephyr.task.services.order;

import org.springframework.data.domain.Pageable;

@FunctionalInterface
public interface OrderProvider {

    Pageable provide(int batchSize);
}
