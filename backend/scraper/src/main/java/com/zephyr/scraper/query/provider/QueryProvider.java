package com.zephyr.scraper.query.provider;

import com.zephyr.data.Keyword;
import com.zephyr.scraper.domain.Request;
import reactor.core.publisher.Mono;

public interface QueryProvider {

    Mono<Request> provide(Keyword keyword);
}
