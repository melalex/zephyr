package com.zephyr.validation.validators;

import com.zephyr.validation.FieldMatch;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;

import java.util.Objects;
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
        String firstObj = BeanUtils.getProperty(value, firstFieldName);
        String secondObj = BeanUtils.getProperty(value, secondFieldName);

        return Objects.equals(firstObj, secondObj);
    }
}
