package com.zephyr.rating.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class PageableFactory {

    @Value("${spring.data.web.pageable.max-page-size:1000}")
    private int pageMaxSize;

    public Pageable create(int page, int size) {
        return PageRequest.of(page, size > pageMaxSize ? pageMaxSize : size);
    }
}
