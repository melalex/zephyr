package com.zephyr.commons;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import lombok.experimental.UtilityClass;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.function.BiFunction;
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

    public <T> Mono<List<T>> reduceToList(Flux<T> flux) {
        return flux.reduce(ImmutableList.<T>builder(), ImmutableList.Builder::add)
                .map(ImmutableList.Builder::build);
    }


}