package com.zephyr.commons;

import lombok.experimental.UtilityClass;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@UtilityClass
public class ReactorUtils {

    public <T> Function<? super Mono<T>, ? extends Publisher<T>> doOnNextAsync(Function<T, Publisher<?>> operation) {
        return i -> i.flatMap(t -> Mono.from(operation.apply(t)).then(Mono.just(t)));
    }
}
