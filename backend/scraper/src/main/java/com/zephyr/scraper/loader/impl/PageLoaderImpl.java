package com.zephyr.scraper.loader.impl;

import com.zephyr.scraper.config.ConfigurationManager;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import com.zephyr.scraper.loader.PageLoader;
import com.zephyr.scraper.loader.browser.Browser;
import com.zephyr.scraper.loader.browser.BrowserFactory;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Component
@RefreshScope
public class PageLoaderImpl implements PageLoader {

    @Setter(onMethod = @__(@Autowired))
    private ConfigurationManager configurationManager;

    @Setter(onMethod = @__(@Autowired))
    private BrowserFactory browserFactory;

    @Override
    public Mono<Response> load(Request request) {
        Browser browser = browserFactory.create(request);

        return toFlux(request)
                .flatMap(browser::browse)
                .collectList()
                .map(h -> Response.of(request.getTask(), request.getProvider(), h));
    }

    private Flux<Request.PageRequest> toFlux(Request request) {
        List<Request.PageRequest> pages = request.getPages();
        long delay = configurationManager.configFor(request.getProvider()).getDelay();

        Mono<Request.PageRequest> first = Mono.justOrEmpty(pages.stream().findFirst());
        Flux<Request.PageRequest> rest = Flux.fromStream(pages.stream().skip(1))
                .delayElements(Duration.ofMillis(delay));

        return Flux.concat(first, rest);
    }
}