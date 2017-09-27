package com.zephyr.errors.domain;

import com.google.common.collect.ImmutableList;
import com.zephyr.errors.ErrorDataBuilder;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
@SuppressWarnings("unused")
public class ErrorData implements Serializable {
    private static final long serialVersionUID = 5782507593253190287L;

    private List<SubjectError> subjectErrors;

    public ErrorData(final List<SubjectError> subjectErrors) {
        this.subjectErrors = ImmutableList.copyOf(subjectErrors);
    }

    public static ErrorDataBuilder builder() {
        return new ErrorDataBuilder();
    }
}
