package com.joshuahawatta.moneyzilla.configurations.validations;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@NoArgsConstructor
public class Validations {
    @Autowired
    Validator validator;

    public <T> void validate(T entity) {

        System.out.println("ENTIDADE "  + entity);

        List<ConstraintViolation<T>> violations = validator.validate(entity).stream().toList();

        System.out.println("VIOLAÇÕES "  + violations);

        if (!violations.isEmpty()) throw new ValidationException(violations.get(0).getMessage());
    }
}
