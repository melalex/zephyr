package com.zephyr.scraper.loader.agent;

import com.zephyr.scraper.loader.context.model.RequestContext;
import org.springframework.web.reactive.function.client.WebClient;

@FunctionalInterface
public interface AgentFactory {

    WebClient create(RequestContext context);
}