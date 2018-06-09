package com.zephyr.errors.dsl;

import com.google.common.collect.Lists;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.Subject;

import java.util.Collection;
import java.util.List;

public final class ErrorDataSpec<T> {

    private final List<Subject> subjects = Lists.newLinkedList();

    private final AssembleCallback<T, ErrorData> callback;

    ErrorDataSpec(AssembleCallback<T, ErrorData> callback) {
        this.callback = callback;
    }

    private ErrorDataSpec<T> addSubjectError(Subject subject) {
        subjects.add(subject);
        return this;
    }

    public ErrorDataSpec<T> subjectErrors(Collection<Subject> errors) {
        subjects.addAll(errors);
        return this;
    }

    public SubjectSpec<ErrorDataSpec<T>> subjectError() {
        return new SubjectSpec<>(this::addSubjectError);
    }

    public T completeData() {
        return callback.onAssemble(ErrorData.of(subjects));
    }
}
