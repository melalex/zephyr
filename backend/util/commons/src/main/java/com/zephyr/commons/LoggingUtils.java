package com.zephyr.commons;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import reactor.retry.RetryContext;

import java.util.function.Consumer;

@UtilityClass
public class LoggingUtils {

    public <T> Consumer<T> info(Logger log, String format) {
        return t -> log.info(format, t);
    }

    public <T extends Throwable> Consumer<T> error(Logger log, String format) {
        return t -> log.error(format, t);
    }

    public <T> Consumer<RetryContext<T>> retryableError(Logger log, String format) {
        return c -> log.error(String.format(format, c.iteration()), c.exception());
    }

    public <T> Consumer<T> debug(Logger log, String format) {
        if (log.isDebugEnabled()) {
            return t -> log.debug(format, t);
        } else {
            return empty();
        }
    }

    private <T> Consumer<T> empty() {
        return t -> {

        };
    }
}