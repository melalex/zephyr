package com.zephyr.validation.validators;

import com.zephyr.validation.AtLeastOneLowerCaseLatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AtLeastOneLowerCaseLatterValidator implements ConstraintValidator<AtLeastOneLowerCaseLatter, String> {
    private static final String regExp = "(?=.*[a-z])";
    private static final Pattern pattern = Pattern.compile(regExp);

    @Override
    public void initialize(AtLeastOneLowerCaseLatter constraintAnnotation) {

    }

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        return pattern.matcher(obj).matches();
    }
}
