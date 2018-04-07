package com.zephyr.commons;

import lombok.experimental.UtilityClass;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.function.Consumer;
import java.util.function.Function;

@UtilityClass
public class ReactorUtils {

    public <T> Function<? super Mono<T>, ? extends Publisher<T>> doOnNextAsync(Function<T, Publisher<?>> operation) {
        return i -> i.flatMap(t -> Mono.from(operation.apply(t)).then(Mono.just(t)));
    }

    public <T> Consumer<T> conditional(boolean condition, Consumer<T> consumer) {
        return t -> {
            if (condition) {
                consumer.accept(t);
            }
        };
    }

    public <L, R, U> Function<? super Mono<Tuple2<L, R>>, ? extends Publisher<Tuple2<U, R>>> mapFirst(
            Function<L, U> mapper) {
        return i -> i.map(t -> Tuples.of(mapper.apply(t.getT1()), t.getT2()));
    }

    public <L, R, U> Function<? super Mono<Tuple2<L, R>>, ? extends Publisher<Tuple2<U, R>>> flatMapFirst(
            Function<L, Mono<U>> mapper) {
        return i -> i.flatMap(t -> mapper.apply(t.getT1()).map(t1 -> Tuples.of(t1, t.getT2())));
    }
}
