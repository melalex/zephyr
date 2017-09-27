package com.zephyr.validation;

import com.zephyr.validation.validators.AtLeastOneLowerCaseLatterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneLowerCaseLatterValidator.class)
@Documented
public @interface AtLeastOneLowerCaseLatter {

    String message() default "{constraints.atLeastOneLowerCaseLatter}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
