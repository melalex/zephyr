package com.zephyr.errors.builders;

import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.Filed;
import com.zephyr.errors.domain.SubjectPath;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.utils.ErrorUtil;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class ExceptionPopulatorTest {
    private static final int STATUS_CODE = 500;
    private static final String EXCEPTION_CODE = "ParameterizedException";
    private static final String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";
    private static final String PAYLOAD = "payload";
    private static final String ROOT = "root";
    private static final String PATH_PART = "pathPart";
    private static final String ACTUAL = "actual";
    private static final String EXPECTED = "field";

    private static final String EXPECTED_CODE = "error.root.pathPart";

    private final ExceptionPopulator testInstance = createTestInstance();

    @Test
    public void shouldBuild() {
        // @formatter:off
        final ParameterizedException exception = testInstance
                .data()
                    .subjectError()
                        .path(SubjectPath.valueOf(ROOT).pathPart(PATH_PART))
                        .payload(payload())
                        .actual(Actual.isA(ACTUAL))
                        .field(Filed.isA(EXPECTED))
                        .add()
                    .complete()
                .status(STATUS_CODE)
                .populate();
        // @formatter:on

        assertEquals(EXPECTED_CODE, ErrorUtil.firstError(exception.getData()).getCode());
        assertEquals(ACTUAL, ErrorUtil.firstError(exception.getData()).getActual().getValue());
        assertEquals(EXPECTED, ErrorUtil.firstError(exception.getData()).getFiled().getValue());
        assertEquals(STATUS_CODE, exception.getStatus());
        assertEquals(EXCEPTION_CODE, exception.getCode());
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
    }

    private Iterable<Object> payload() {
        return Collections.singleton(PAYLOAD);
    }

    private ExceptionPopulator createTestInstance() {
        return ExceptionPopulator.of(new ParameterizedException(EXCEPTION_MESSAGE));
    }
}