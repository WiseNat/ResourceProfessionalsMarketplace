package com.wise.ResourceProfessionalsMarketplace.util;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;

@Component
public class ValidatorUtil {
    public <T> String getFieldFromConstraintViolation(ConstraintViolation<T> constraintViolation) {
        return constraintViolation.getPropertyPath().toString();
    }
}
