package com.zephyr.validation.validators;

import com.zephyr.validation.FieldMatch;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.first();
        secondFieldName = constraintAnnotation.second();
    }

    @Override
    @SneakyThrows
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object firstObj = BeanUtils.getProperty(value, firstFieldName);
        Object secondObj = BeanUtils.getProperty(value, secondFieldName);

        return firstObj == null && secondObj == null
               || firstObj != null && firstObj.equals(secondObj);
    }
}
