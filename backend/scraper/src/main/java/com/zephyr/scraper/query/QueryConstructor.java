package com.zephyr.scraper.query;

import com.zephyr.data.Keyword;
import com.zephyr.scraper.domain.Request;
import reactor.core.publisher.Flux;

public interface QueryConstructor {

    Flux<Request> construct(Keyword keyword);
}
