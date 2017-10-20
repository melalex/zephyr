package com.zephyr.scraper.loader.impl;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.loader.PageLoader;
import com.zephyr.scraper.loader.browser.BrowserFactory;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class PageLoaderImpl implements PageLoader {

    @Setter(onMethod = @__(@Autowired))
    private BrowserFactory browserFactory;

    @Override
    public Mono<Response> load(Request request) {
        return Flux.fromIterable(request.getPages())
                .flatMap(p -> browserFactory.create(request).browse(p))
                .collectList()
                .map(h -> Response.of(request.getTask(), request.getProvider(), h));
    }
}