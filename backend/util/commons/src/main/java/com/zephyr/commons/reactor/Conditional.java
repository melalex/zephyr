package com.zephyr.commons.reactor;

import java.util.concurrent.Flow;
import java.util.function.Function;

public class Conditional<T, R> {
    private Flow.Publisher<R> ifTrue;
    private Flow.Publisher<R> ifFalse;

    public Function<Boolean, Flow.Publisher<R>> end() {
        return c -> c ? ifTrue : ifFalse;
    }
}
