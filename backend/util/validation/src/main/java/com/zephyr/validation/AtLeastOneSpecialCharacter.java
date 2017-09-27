package com.zephyr.validation;

import com.zephyr.validation.validators.AtLeastOneSpecialCharacterValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AtLeastOneSpecialCharacterValidator.class)
@Documented
public @interface AtLeastOneSpecialCharacter {

    String message() default "{constraints.atLeastOneSpecialCharacter}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
