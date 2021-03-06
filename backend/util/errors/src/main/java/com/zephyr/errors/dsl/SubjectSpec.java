package com.zephyr.errors.dsl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.Path;
import com.zephyr.errors.domain.Reason;
import com.zephyr.errors.domain.Subject;
import com.zephyr.errors.utils.ErrorUtil;

import java.util.stream.Collectors;

public final class SubjectSpec<T> {

    private final AssembleCallback<T, Subject> callback;
    private Path path;
    private Actual actual;
    private Reason reason;
    private Iterable<Object> payload;

    SubjectSpec(AssembleCallback<T, Subject> callback) {
        this.callback = callback;
    }

    public SubjectSpec<T> path(Path path) {
        this.path = path;
        return this;
    }

    public PathSpec<SubjectSpec<T>> path() {
        return new PathSpec<>(this::path);
    }

    public SubjectSpec<T> actual(Actual actual) {
        this.actual = actual;
        return this;
    }

    public SubjectSpec<T> reason(Reason reason) {
        this.reason = reason;
        return this;
    }

    public SubjectSpec<T> payload(Iterable<Object> payload) {
        this.payload = Streams.stream(payload)
                .map(ErrorUtil::wrapValue)
                .collect(Collectors.toList());

        return this;
    }

    public SubjectSpec<T> payload(Object payload) {
        return payload(ImmutableList.of(payload));
    }

    public SubjectSpec<T> payload(Object first, Object... other) {
        Iterable<Object> payload = ImmutableList.builder()
                .add(first)
                .add(other)
                .build();

        return payload(payload);
    }

    public PayloadSpec<SubjectSpec<T>> payload() {
        return new PayloadSpec<>(this::payload);
    }

    public T completeSubject() {
        return callback.onAssemble(new Subject(path, actual, reason, payload));
    }
}
