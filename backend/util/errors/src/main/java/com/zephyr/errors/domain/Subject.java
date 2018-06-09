package com.zephyr.errors.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.zephyr.errors.utils.ErrorUtil;
import lombok.Value;

import java.io.Serializable;

@Value
public class Subject implements Serializable {

    private static final long serialVersionUID = -5358302471268083113L;

    private Path path;
    private Actual actual;
    private Reason reason;
    private Iterable<Object> payload;

    public Subject(Path path, Actual actual, Reason reason, Iterable<Object> payload) {
        this.path = path;
        this.actual = actual;
        this.reason = reason;
        this.payload = ImmutableList.copyOf(payload);
    }

    public Object[] getPayloadAsArray() {
        return Iterables.toArray(payload, Object.class);
    }

    public String getCode() {
        return ErrorUtil.ERROR_CODE_JOINER
                .skipNulls()
                .join(ErrorUtil.ERROR_CODE_PREFIX, path.getFullPathCode(), reason.getValue());
    }
}
