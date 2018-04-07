package com.zephyr.validation;

import com.zephyr.validation.validators.AtLeastOneDigitValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneDigitValidator.class)
@Documented
public @interface AtLeastOneDigit {

    String message() default "{constraints.atLeastOneDigit}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
