package com.zephyr.errors.domain;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.zephyr.errors.utils.ErrorUtil;
import lombok.Value;

import java.io.Serializable;

@Value
public class SubjectError implements Serializable {
    private static final long serialVersionUID = -5358302471268083113L;

    private SubjectPath path;
    private Actual actual;
    private Field field;
    private Iterable<Object> payload;

    public SubjectError(final SubjectPath path, final Actual actual, final Field field, final Iterable<Object> payload) {
        this.path = path;
        this.actual = actual;
        this.field = field;
        this.payload = ImmutableList.copyOf(payload);
    }

    public Object[] getPayloadAsArray() {
        return Iterables.toArray(payload, Object.class);
    }

    public String getCode() {
        return Joiner
                .on(ErrorUtil.ERROR_CODE_SEPARATOR)
                .skipNulls()
                .join(ErrorUtil.ERROR_CODE_PREFIX, path.getFullPathCode());
    }
}
