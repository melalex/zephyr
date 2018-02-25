package com.zephyr.errors.dsl;

import com.zephyr.errors.exceptions.InconsistentModelException;
import com.zephyr.errors.exceptions.OwnershipException;
import com.zephyr.errors.exceptions.ResourceNotFoundException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Exceptions {

    public InconsistentModelException inconsistent(String message) {
        return new InconsistentModelException(message);
    }

    public OwnershipException ownership(String message) {
        return new OwnershipException(message);
    }

    public ResourceNotFoundException notFound(String message) {
        return new ResourceNotFoundException(message);
    }
}
