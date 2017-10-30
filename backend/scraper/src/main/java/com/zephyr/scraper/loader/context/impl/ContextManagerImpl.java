package com.zephyr.scraper.loader.context.impl;

import com.zephyr.data.enums.SearchEngine;
import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.context.ContextManager;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.loader.context.strategy.RequestStrategy;
import com.zephyr.scraper.properties.ScraperProperties;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RefreshScope
public class ContextManagerImpl implements ContextManager {

    @Setter(onMethod = @__(@Autowired))
    private ScraperProperties scraperProperties;

    @Setter(onMethod = @__(@Autowired))
    private RequestStrategy directRequestStrategy;

    @Setter(onMethod = @__(@Autowired))
    private RequestStrategy proxyRequestStrategy;

    @Override
    public Mono<RequestContext> toContext(Request request, PageRequest page) {
        if (isUseProxy(request.getProvider())) {
            return proxyRequestStrategy.toContext(request, page);
        }

        return directRequestStrategy.toContext(request, page);
    }

    @Override
    public void report(RequestContext context) {
        if (isUseProxy(context.getProvider())) {
            proxyRequestStrategy.report(context);
        }

        directRequestStrategy.report(context);
    }

    private boolean isUseProxy(SearchEngine engine) {
        return scraperProperties.getScraper(engine).isUseProxy();
    }
}
