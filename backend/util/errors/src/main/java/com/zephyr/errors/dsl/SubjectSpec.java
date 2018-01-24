package com.zephyr.errors.dsl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.zephyr.errors.domain.*;
import com.zephyr.errors.utils.ErrorUtil;

import java.util.stream.Collectors;

public final class SubjectSpec<T> {
    private SubjectPath path;
    private Actual actual;
    private Field field;
    private Iterable<Object> payload;

    private final AssembleCallback<T, SubjectError> callback;

    SubjectSpec(AssembleCallback<T, SubjectError> callback) {
        this.callback = callback;
    }

    public SubjectSpec<T> path(SubjectPath path) {
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

    public SubjectSpec<T> field(Field field) {
        this.field = field;
        return this;
    }

    public SubjectSpec<T> payload(Iterable<Object> payload) {
        this.payload = Streams.stream(payload)
                .map(ErrorUtil::wrapValue)
                .collect(Collectors.toList());

        return this;
    }

    public SubjectSpec<T> payload(Object payload) {
        this.payload = ImmutableList.of(ErrorUtil.wrapValue(payload));
        return this;
    }

    public PayloadSpec<SubjectSpec<T>> payload() {
        return new PayloadSpec<>(this::payload);
    }

    public T completeSubject() {
        return callback.onAssemble(new SubjectError(path, actual, field, payload));
    }
}
