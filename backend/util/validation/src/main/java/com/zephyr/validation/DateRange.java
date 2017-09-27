package com.zephyr.validation;

import com.zephyr.validation.validators.DateRangeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author Alexander Melashchenko
 * @version 1.0 27 Jul 2017
 */
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateRangeValidator.class)
@Documented
public @interface DateRange {
    String message() default "{constraints.dateRange}";

    String code() default "constraints.dateRange";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String from();

    String to();
}
