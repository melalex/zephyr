package com.zephyr.validation;

import com.zephyr.validation.validators.AtLeastOneDigitValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneDigitValidator.class)
@Documented
public @interface AtLeastOneDigit {

    String message() default "{constraints.atLeastOneDigit}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
