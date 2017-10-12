package com.zephyr.commons;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@UtilityClass
public class DataAccessUtils {

    public <T> Mono<Page<T>> toReactivePage(Flux<T> content, Mono<Long> count, Pageable pageable) {
        return Mono.zip(ReactorUtils.reduceToList(content), count)
                .map(t -> new PageImpl<>(t.getT1(), pageable, t.getT2()));
    }
}
