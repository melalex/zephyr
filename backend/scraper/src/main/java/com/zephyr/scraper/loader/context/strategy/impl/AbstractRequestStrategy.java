package com.zephyr.scraper.loader.context.strategy.impl;

import com.zephyr.scraper.domain.PageRequest;
import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.loader.context.model.RequestContext;
import com.zephyr.scraper.loader.context.strategy.RequestStrategy;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;

public abstract class AbstractRequestStrategy implements RequestStrategy {

    @Setter(onMethod = @__(@Autowired))
    private ModelMapper mapper;

    @Override
    public Mono<RequestContext> toContext(Request request, PageRequest page) {
        RequestContext context = mapper.map(request, RequestContext.class);
        context.setPage(page);

        return configure(context);
    }

    protected abstract Mono<RequestContext> configure(RequestContext context);
}
