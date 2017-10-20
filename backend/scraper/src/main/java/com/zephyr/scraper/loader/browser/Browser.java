package com.zephyr.scraper.loader.browser;

import com.zephyr.scraper.domain.Request;
import com.zephyr.scraper.domain.Response;
import reactor.core.publisher.Mono;

public interface Browser {

    Mono<Response.PageResponse> browse(Request.PageRequest request);
}
