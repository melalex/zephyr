package com.zephyr.validation.validators;

import com.zephyr.validation.AtLeastOneUpperCaseLatter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AtLeastOneUpperCaseLatterValidator implements ConstraintValidator<AtLeastOneUpperCaseLatter, String> {
    private static final String REG_EXP = "(?=.*[A-Z])";
    private static final Pattern PATTERN = Pattern.compile(REG_EXP);

    @Override
    public void initialize(AtLeastOneUpperCaseLatter constraintAnnotation) {

    }

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        return PATTERN.matcher(obj).matches();
    }
}
