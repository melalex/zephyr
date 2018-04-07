package com.zephyr.validation.validators;

import com.zephyr.validation.AtLeastOneDigit;

import java.util.regex.Pattern;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AtLeastOneDigitValidator implements ConstraintValidator<AtLeastOneDigit, String> {

    private static final String REG_EXP = "(?=.*[0-9])";
    private static final Pattern PATTERN = Pattern.compile(REG_EXP);

    @Override
    public void initialize(AtLeastOneDigit constraintAnnotation) {

    }

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        return PATTERN.matcher(obj).matches();
    }
}

