package com.zephyr.errors.dsl;

import com.google.common.collect.Lists;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.SubjectError;
import lombok.NoArgsConstructor;
import lombok.experimental.Delegate;

import java.util.Collection;
import java.util.List;

@NoArgsConstructor(staticName = "from")
public final class ErrorDataSpec {
    private final List<SubjectError> subjectErrors = Lists.newLinkedList();

    private ErrorDataSpec addSubjectError(final SubjectError subjectError) {
        subjectErrors.add(subjectError);
        return this;
    }

    public SubordinateSubjectSpec subjectError() {
        return new SubordinateSubjectSpec(this);
    }

    public ErrorDataSpec subjectErrors(Collection<SubjectError> errors) {
        subjectErrors.addAll(errors);
        return this;
    }

    public ErrorData create() {
        return ErrorData.of(subjectErrors);
    }

    public static final class SubordinateSubjectSpec {

        @Delegate(excludes = Creation.class)
        private final SubjectSpec subjectSpec;
        private final ErrorDataSpec errorDataSpec;

        SubordinateSubjectSpec(final ErrorDataSpec errorDataSpec) {
            this.errorDataSpec = errorDataSpec;
            this.subjectSpec = SubjectSpec.from();
        }

        public ErrorDataSpec add() {
            return errorDataSpec.addSubjectError(subjectSpec.create());
        }
    }
}
