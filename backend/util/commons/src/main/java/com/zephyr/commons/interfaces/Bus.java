package com.zephyr.commons.interfaces;

import reactor.core.publisher.Flux;

@FunctionalInterface
public interface Bus<T, R> {

    Flux<R> updatesFor(T criteria);
}
