package com.zephyr.scraper.loader.impl;

import com.zephyr.data.Keyword;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.loader.PageLoader;
import reactor.core.publisher.Mono;

public abstract class AbstractPageLoader implements PageLoader {

    @Override
    public Mono<Response> load(Request request) {
        return getHtml(request.getKeyword())
                .map(h -> Response.of(request.getKeyword(), request.getProvider(), h));
    }

    protected abstract Mono<String> getHtml(Keyword keyword);
}