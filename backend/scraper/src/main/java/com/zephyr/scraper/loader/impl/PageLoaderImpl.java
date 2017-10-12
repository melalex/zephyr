package com.zephyr.scraper.loader.impl;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.loader.PageLoader;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class PageLoaderImpl implements PageLoader {

    @Override
    public Mono<Response> load(Request request) {
        return null;
    }
}
