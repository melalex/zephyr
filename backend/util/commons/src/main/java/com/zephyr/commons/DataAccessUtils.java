package com.zephyr.commons;

import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@UtilityClass
public class DataAccessUtils {
    private static final int FIRST_PAGE = 0;

    public <T> Mono<Page<T>> toReactivePage(Flux<T> content, Mono<Long> count, Pageable pageable) {
        return toReactivePage(content.collectList(), count, pageable);
    }

    public <T> Mono<Page<T>> toReactivePage(Flux<T> content) {
        Mono<List<T>> reduced = content.collectList();
        Mono<Long> count = reduced.map(List::size).map(Integer::longValue);

        return toReactivePage(reduced, count);
    }

    public <T> Mono<Page<T>> toReactivePage(Flux<T> content, Mono<Long> count) {
        return toReactivePage(content.collectList(), count);
    }

    private <T> Mono<Page<T>> toReactivePage(Mono<List<T>> content, Mono<Long> count) {
        return toReactivePage(content, count, PageRequest.of(FIRST_PAGE, Integer.MAX_VALUE));
    }

    private <T> Mono<Page<T>> toReactivePage(Mono<List<T>> content, Mono<Long> count, Pageable pageable) {
        return Mono.zip(content, count)
                .map(t -> new PageImpl<>(t.getT1(), pageable, t.getT2()));
    }
}