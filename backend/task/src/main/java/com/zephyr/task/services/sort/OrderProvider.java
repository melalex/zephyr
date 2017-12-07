package com.zephyr.task.services.sort;

import org.springframework.data.domain.Sort;

@FunctionalInterface
public interface OrderProvider {

    Sort provide();
}
