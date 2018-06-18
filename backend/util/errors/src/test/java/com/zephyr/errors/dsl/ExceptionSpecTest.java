package com.zephyr.errors.dsl;

import static org.junit.Assert.assertEquals;

import com.zephyr.errors.domain.Actual;
import com.zephyr.errors.domain.Path;
import com.zephyr.errors.domain.Reason;
import com.zephyr.errors.exceptions.ParameterizedException;
import com.zephyr.errors.utils.ErrorUtil;
import org.junit.Test;

import java.util.List;

public class ExceptionSpecTest {

    private static final int STATUS_CODE = 500;

    private static final String EXCEPTION_CODE = "ParameterizedException";
    private static final String EXCEPTION_MESSAGE = "EXCEPTION_MESSAGE";
    private static final String PAYLOAD = "payload";
    private static final String ROOT = "root";
    private static final String PATH_PART = "pathPart";
    private static final String ACTUAL = "actual";
    private static final String REASON = "reason";
    private static final String EXPECTED_CODE = "error.root.pathPart.reason";

    @Test
    public void shouldBuild() {
        // @formatter:off
        final var exception = Problems.exception(new ParameterizedException(EXCEPTION_MESSAGE))
                .status(STATUS_CODE)
                .data()
                    .subjectError()
                        .path(Path.of(ROOT).to(PATH_PART))
                        .payload(List.of(PAYLOAD))
                        .actual(Actual.isA(ACTUAL))
                        .reason(Reason.isA(REASON))
                        .completeSubject()
                    .completeData()
                .populate();
        // @formatter:on

        assertEquals(EXPECTED_CODE, ErrorUtil.firstError(exception.getData()).getCode());
        assertEquals(ACTUAL, ErrorUtil.firstError(exception.getData()).getActual().getValue());
        assertEquals(STATUS_CODE, exception.getStatus());
        assertEquals(EXCEPTION_CODE, exception.getCode());
        assertEquals(EXCEPTION_MESSAGE, exception.getMessage());
    }
}