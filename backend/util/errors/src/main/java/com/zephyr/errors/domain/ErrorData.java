package com.zephyr.errors.domain;

import com.google.common.collect.ImmutableList;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

@Value
public class ErrorData implements Serializable {
    private static final long serialVersionUID = 5782507593253190287L;

    private List<Subject> subjects;

    private ErrorData(List<Subject> subjects) {
        this.subjects = ImmutableList.copyOf(subjects);
    }

    public static ErrorData of(List<Subject> subjects) {
        return new ErrorData(subjects);
    }

    public boolean hasErrors() {
        return !subjects.isEmpty();
    }
}
