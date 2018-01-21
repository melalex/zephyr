package com.zephyr.errors.dsl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Streams;
import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.Field;
import com.zephyr.errors.domain.SubjectError;
import com.zephyr.errors.domain.SubjectPath;
import com.zephyr.errors.utils.ErrorUtil;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@NoArgsConstructor(staticName = "from")
public final class SubjectSpec {
    private SubjectPath path;
    private Actual actual;
    private Field field;
    private Iterable<Object> payload;

    public SubjectSpec path(final SubjectPath path) {
        this.path = path;
        return this;
    }

    public SubjectPathSpec path() {
        return new SubjectPathSpec(this);
    }

    public SubjectSpec actual(final Actual actual) {
        this.actual = actual;
        return this;
    }

    public SubjectSpec field(final Field field) {
        this.field = field;
        return this;
    }

    public SubjectSpec payload(final Iterable<Object> payload) {
        this.payload = Streams.stream(payload)
                .map(ErrorUtil::wrapValue)
                .collect(Collectors.toList());

        return this;
    }

    public SubjectSpec payload(final Object payload) {
        this.payload = ImmutableList.of(ErrorUtil.wrapValue(payload));
        return this;
    }

    public PayloadSpec payload() {
        return new PayloadSpec(this);
    }

    public SubjectError create() {
        return new SubjectError(path, actual, field, payload);
    }

    public static final class SubjectPathSpec {
        private static final String ROOT_ASSERTION_MESSAGE = "root can't be null";

        private final SubjectSpec subjectSpec;

        private String root;
        private final List<String> path = Lists.newLinkedList();

        SubjectPathSpec(final SubjectSpec subjectSpec) {
            this.subjectSpec = subjectSpec;
        }

        public SubjectPathSpec root(final String root) {
            this.root = root;
            return this;
        }

        public SubjectPathSpec with(final String pathPart) {
            path.add(pathPart);
            return this;
        }

        public SubjectPathSpec with(final Enum<?> value) {
            return with(ErrorUtil.toCamel(value));
        }


        public SubjectSpec add() {
            Preconditions.checkNotNull(root, ROOT_ASSERTION_MESSAGE);

            return subjectSpec.path(SubjectPath.valueOf(root).pathPart(path));
        }
    }

    public static final class PayloadSpec {
        private static final String EMPTY = "' '";

        private final SubjectSpec subjectSpec;

        private final List<String> payload = Lists.newLinkedList();

        PayloadSpec(final SubjectSpec subjectSpec) {
            this.subjectSpec = subjectSpec;
        }

        public PayloadSpec with(final Object payload) {
            String value = Optional.ofNullable(payload)
                    .map(ErrorUtil::wrapValue)
                    .orElse(EMPTY);

            this.payload.add(value);
            return this;
        }

        public SubjectSpec add() {
            return subjectSpec.payload(payload);
        }
    }
}
