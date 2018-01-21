package com.zephyr.commons.interfaces;

import reactor.core.publisher.Mono;

@FunctionalInterface
public interface Assembler<S, D> {

    Mono<D> assemble(S source);
}
