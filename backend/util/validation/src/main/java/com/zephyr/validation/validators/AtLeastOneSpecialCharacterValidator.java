package com.zephyr.validation.validators;

import com.zephyr.validation.AtLeastOneSpecialCharacter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AtLeastOneSpecialCharacterValidator implements ConstraintValidator<AtLeastOneSpecialCharacter, String> {
    private static final String REG_EXP = "(?=.*[@#$%^&+=])";
    private static final Pattern PATTERN = Pattern.compile(REG_EXP);

    @Override
    public void initialize(AtLeastOneSpecialCharacter constraintAnnotation) {

    }

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        return PATTERN.matcher(obj).matches();
    }
}
