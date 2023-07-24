package com.joshuahawatta.moneyzilla.configurations.validations;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class Validations {
    @Autowired
    Validator validator;

    private Validations() {}

    /** @param entity search the validations of an entity and if there´s any violation to throw an exception. */
    public <T> void validate(T entity) {
        List<ConstraintViolation<T>> violations = validator.validate(entity).stream().toList();

        if (!violations.isEmpty()) throw new ValidationException(violations.get(0).getMessage());
    }
}
