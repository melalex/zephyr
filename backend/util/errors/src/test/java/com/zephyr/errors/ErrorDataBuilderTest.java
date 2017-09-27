package com.zephyr.errors;

import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.ErrorData;
import com.zephyr.errors.domain.Expected;
import com.zephyr.errors.domain.SubjectPath;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ErrorDataBuilderTest {
    private static final String PAYLOAD = "payload";
    private static final String ROOT = "root";
    private static final String PATH_PART = "pathPart";
    private static final String ACTUAL = "actual";
    private static final String EXPECTED = "expected";

    private static final String EXPECTED_CODE = "error.root.pathPart.actual.expected";

    private final ErrorDataBuilder testInstance = new ErrorDataBuilder();

    @Test
    public void shouldBuild() {
        final ErrorData errorData = testInstance
                .subjectError()
                    .path(SubjectPath.valueOf(ROOT).pathPart(PATH_PART))
                    .payload(payload())
                    .actual(Actual.isA(ACTUAL))
                    .expected(Expected.isA(EXPECTED))
                    .add()
                .build();

        Assert.assertEquals(EXPECTED_CODE, ErrorUtil.firstError(errorData).getCode());
    }

    private Iterable<Object> payload() {
        return Collections.singleton(PAYLOAD);
    }
}