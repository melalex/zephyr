package com.zephyr.validation.validators;

import com.zephyr.validation.AtLeastOneDigit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AtLeastOneDigitValidator implements ConstraintValidator<AtLeastOneDigit, String> {
    private static final String regExp = "(?=.*[0-9])";
    private static final Pattern pattern = Pattern.compile(regExp);

    @Override
    public void initialize(AtLeastOneDigit constraintAnnotation) {

    }

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        return pattern.matcher(obj).matches();
    }
}

