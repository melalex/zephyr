package com.zephyr.validation.validators;

import com.zephyr.validation.AtLeastOneUpperCaseLatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AtLeastOneUpperCaseLatterValidator implements ConstraintValidator<AtLeastOneUpperCaseLatter, String> {
    private static final String regExp = "(?=.*[A-Z])";
    private static final Pattern pattern = Pattern.compile(regExp);

    @Override
    public void initialize(AtLeastOneUpperCaseLatter constraintAnnotation) {

    }

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        return pattern.matcher(obj).matches();
    }
}
