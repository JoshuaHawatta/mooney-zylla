package com.joshuahawatta.moneyzilla.helpers.exceptions;

import com.joshuahawatta.moneyzilla.helpers.Message;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class EntitiesExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(ValidationException exception) {
        return new ResponseEntity<>(Message.asJson(exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleArgumentsException(IllegalArgumentException exception) {
        return new ResponseEntity<>(Message.asJson(exception.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
