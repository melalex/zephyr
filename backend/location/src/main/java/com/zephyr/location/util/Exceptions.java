package com.zephyr.location.util;

import com.zephyr.location.exception.ResourceNotFoundException;
import lombok.experimental.UtilityClass;

import java.util.function.Supplier;

@UtilityClass
public class Exceptions {

    public Supplier<ResourceNotFoundException> newNotFoundError(Class<?> clazz, Object id) {
        return () -> {
            String message = String.format("[ %s ] with id [ %s ] not found", clazz.getSimpleName(), id);
            return new ResourceNotFoundException(message);
        };
    }

}
