package com.zephyr.validation.validators;

import com.zephyr.validation.AtLeastOneSpecialCharacter;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class AtLeastOneSpecialCharacterValidator implements ConstraintValidator<AtLeastOneSpecialCharacter, String> {
    private static final String regExp = "(?=.*[@#$%^&+=])";
    private static final Pattern pattern = Pattern.compile(regExp);

    @Override
    public void initialize(AtLeastOneSpecialCharacter constraintAnnotation) {

    }

    @Override
    public boolean isValid(String obj, ConstraintValidatorContext context) {
        return pattern.matcher(obj).matches();
    }
}
