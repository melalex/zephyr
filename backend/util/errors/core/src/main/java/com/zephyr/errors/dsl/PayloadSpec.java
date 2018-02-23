package com.zephyr.errors.dsl;

import com.google.common.collect.Lists;
import com.zephyr.errors.utils.ErrorUtil;

import java.util.List;
import java.util.Optional;

public final class PayloadSpec<T> {
    private static final String EMPTY = "' '";

    private final AssembleCallback<T, List<String>> callback;

    private final List<String> payload = Lists.newLinkedList();

    PayloadSpec(AssembleCallback<T, List<String>> callback) {
        this.callback = callback;
    }

    public PayloadSpec<T> with(Object payload) {
        String value = Optional.ofNullable(payload)
                .map(ErrorUtil::wrapValue)
                .orElse(EMPTY);

        this.payload.add(value);
        return this;
    }

    public T completePayload() {
        return callback.onAssemble(payload);
    }
}
