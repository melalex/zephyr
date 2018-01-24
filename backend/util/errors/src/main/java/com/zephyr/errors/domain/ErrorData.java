package com.zephyr.errors.domain;

import com.google.common.collect.ImmutableList;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class ErrorData implements Serializable {
    private static final long serialVersionUID = 5782507593253190287L;

    private List<SubjectError> subjectErrors;

    public ErrorData(List<SubjectError> subjectErrors) {
        this.subjectErrors = ImmutableList.copyOf(subjectErrors);
    }

    public static ErrorData of(List<SubjectError> subjectErrors) {
        return new ErrorData(subjectErrors);
    }

    public boolean hasErrors() {
        return !subjectErrors.isEmpty();
    }
}
