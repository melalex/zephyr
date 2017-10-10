package com.zephyr.errors.domain;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import lombok.Value;

import java.io.Serializable;

@Value
public class SubjectError implements Serializable {
    private static final long serialVersionUID = -5358302471268083113L;

    public final String ERROR_CODE_PREFIX = "error";
    public final String ERROR_CODE_SEPARATOR = ".";

    private SubjectPath path;
    private Actual actual;
    private Filed filed;
    private Iterable<Object> payload;

    public SubjectError(final SubjectPath path, final Actual actual, final Filed filed, final Iterable<Object> payload) {
        this.path = path;
        this.actual = actual;
        this.filed = filed;
        this.payload = ImmutableList.copyOf(payload);
    }

    public Object[] getPayloadAsArray() {
        return Iterables.toArray(payload, Object.class);
    }

    public String getCode() {
        return Joiner
                .on(ERROR_CODE_SEPARATOR)
                .skipNulls()
                .join(ERROR_CODE_PREFIX, path.getFullPathCode());
    }
}
