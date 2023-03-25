package com.joshuahawatta.moneyzilla.configurations;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class Validations {
    @Autowired
    Validator validator;

    private Validations() {}

    public <T> void setValidations(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) throw new ValidationException("Usuário inválido!", (Throwable) violations);
    }
}
