package com.zephyr.commons;

import com.google.common.collect.ImmutableList;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@UtilityClass
public class ReactorUtils {

    public <T> Mono<List<T>> reduceToList(Flux<T> flux) {
        return flux.reduce(ImmutableList.<T>builder(), ImmutableList.Builder::add)
                .map(ImmutableList.Builder::build);
    }
}