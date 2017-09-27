package com.zephyr.commons;

import lombok.experimental.UtilityClass;

import java.util.function.Consumer;
import org.slf4j.Logger;

@UtilityClass
public class LoggingUtils {

    public <T> Consumer<T> info(Logger log, String format) {
        return t -> log.info(format, t);
    }
}