package com.zephyr.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@NotNull
@Size(min = 6, max = 255)
@AtLeastOneDigit
@AtLeastOneLowerCaseLatter
@AtLeastOneUpperCaseLatter
@AtLeastOneSpecialCharacter
public @interface HardPassword {

}
