package com.zephyr.scraper.loader.agent;

import com.zephyr.scraper.domain.Request;
import org.springframework.web.reactive.function.client.WebClient;

@FunctionalInterface
public interface WebClientFactory {

    WebClient create(Request request);
}