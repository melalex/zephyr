package com.zephyr.dictionary.services.sort;

import org.springframework.data.domain.Sort;

@FunctionalInterface
public interface OrderProvider {

    Sort provide();
}
