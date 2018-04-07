package com.zephyr.validation;

import com.zephyr.validation.validators.AtLeastOneLowerCaseLatterValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneLowerCaseLatterValidator.class)
@Documented
public @interface AtLeastOneLowerCaseLatter {

    String message() default "{constraints.atLeastOneLowerCaseLatter}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
