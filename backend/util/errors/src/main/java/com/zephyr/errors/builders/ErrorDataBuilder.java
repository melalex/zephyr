package com.zephyr.errors.builders;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.zephyr.errors.domain.*;
import com.zephyr.errors.exceptions.ParameterizedException;

import java.util.List;

@SuppressWarnings("unused, WeakerAccess")
public final class ErrorDataBuilder<T extends ParameterizedException> {
    private ExceptionPopulator<T> exceptionPopulator;
    private final List<SubjectError> subjectErrors = Lists.newLinkedList();

    public ErrorDataBuilder(ExceptionPopulator<T> exceptionPopulator) {
        this.exceptionPopulator = exceptionPopulator;
    }

    private ErrorDataBuilder addSubjectError(final SubjectError subjectError) {
        subjectErrors.add(subjectError);
        return this;
    }

    public SubjectBuilder subjectError() {
        return new SubjectBuilder(this);
    }

    public ExceptionPopulator<T> complete() {
        return exceptionPopulator.onDataBuilt(new ErrorData(subjectErrors));
    }

    public static final class SubjectBuilder {
        private final ErrorDataBuilder errorDataBuilder;

        private SubjectPath path;
        private Actual actual;
        private Filed filed;
        private Iterable<Object> payload;

        SubjectBuilder(final ErrorDataBuilder errorDataBuilder) {
            this.errorDataBuilder = errorDataBuilder;
        }

        public SubjectBuilder path(final SubjectPath path) {
            this.path = path;
            return this;
        }

        public SubjectPathBuilder path() {
            return new SubjectPathBuilder(this);
        }

        public SubjectBuilder actual(final Actual actual) {
            this.actual = actual;
            return this;
        }

        public SubjectBuilder expected(final Filed filed) {
            this.filed = filed;
            return this;
        }

        public SubjectBuilder payload(final Iterable<Object> payload) {
            this.payload = ImmutableList.copyOf(payload);
            return this;
        }

        public PayloadBuilder payload() {
            return new PayloadBuilder(this);
        }

        public ErrorDataBuilder add() {
            return errorDataBuilder.addSubjectError(new SubjectError(path, actual, filed, payload));
        }
    }

    public static final class SubjectPathBuilder {
        private static final String ROOT_ASSERTION_MESSAGE = "root can't be null";

        private final SubjectBuilder subjectBuilder;

        private String root;
        private final List<String> path = Lists.newLinkedList();

        SubjectPathBuilder(final SubjectBuilder subjectBuilder) {
            this.subjectBuilder = subjectBuilder;
        }

        public SubjectPathBuilder root(final String root) {
            this.root = root;
            return this;
        }

        public SubjectPathBuilder with(final String pathPart) {
            path.add(pathPart);
            return this;
        }

        public SubjectBuilder add() {
            Preconditions.checkNotNull(root, ROOT_ASSERTION_MESSAGE);

            return subjectBuilder.path(SubjectPath.valueOf(root).pathPart(path));
        }
    }

    public static final class PayloadBuilder {
        private final SubjectBuilder subjectBuilder;

        private final List<Object> payload = Lists.newLinkedList();

        PayloadBuilder(final SubjectBuilder subjectBuilder) {
            this.subjectBuilder = subjectBuilder;
        }

        public PayloadBuilder with(final Object payload) {
            this.payload.add(payload);
            return this;
        }

        public SubjectBuilder add() {
            return subjectBuilder.payload(payload);
        }
    }
}
