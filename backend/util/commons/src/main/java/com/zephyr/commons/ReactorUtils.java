package com.zephyr.commons;

import lombok.experimental.UtilityClass;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@UtilityClass
public class ReactorUtils {

    public <T> Function<Flux<Mono<T>>, Flux<T>> joiner() {
        return target -> {
            Flux<T> result = Flux.empty();

            target.subscribe(result::mergeWith);

            return result;
        };
    }
}
