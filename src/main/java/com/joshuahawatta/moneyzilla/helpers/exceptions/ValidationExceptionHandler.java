package com.joshuahawatta.moneyzilla.helpers.exceptions;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.joshuahawatta.moneyzilla.helpers.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Map;

@ControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Map<String, String>> handleNotFoundException(NullPointerException exception) {
        return new ResponseEntity<>(Message.asJson(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(AccessDeniedException exception) {
        return new ResponseEntity<>(Message.asJson(exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<Map<String, String>> handleTokenExpiredException(TokenExpiredException exception) {
        return new ResponseEntity<>(Message.asJson(exception.getMessage()), HttpStatus.UNAUTHORIZED);
    }
}
