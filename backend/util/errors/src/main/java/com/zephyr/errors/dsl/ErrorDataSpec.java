package com.zephyr.errors.dsl;

import com.google.common.collect.Lists;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.SubjectError;

import java.util.Collection;
import java.util.List;

public final class ErrorDataSpec<T> {
    private final List<SubjectError> subjectErrors = Lists.newLinkedList();

    private final AssembleCallback<T, ErrorData> callback;

    ErrorDataSpec(AssembleCallback<T, ErrorData> callback) {
        this.callback = callback;
    }

    private ErrorDataSpec<T> addSubjectError(SubjectError subjectError) {
        subjectErrors.add(subjectError);
        return this;
    }

    public ErrorDataSpec<T> subjectErrors(Collection<SubjectError> errors) {
        subjectErrors.addAll(errors);
        return this;
    }

    public SubjectSpec<ErrorDataSpec<T>> subjectError() {
        return new SubjectSpec<>(this::addSubjectError);
    }

    public T completeData() {
        return callback.onAssemble(ErrorData.of(subjectErrors));
    }
}
