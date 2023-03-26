package com.joshuahawatta.moneyzilla.entities.exceptions;

import com.joshuahawatta.moneyzilla.entities.responses.Response;
import com.joshuahawatta.moneyzilla.entities.responses.ResponseResult;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EntityExceptionsHandler extends MainExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<Response<String>> handleUnprocessableEntityException(ValidationException exception) {
        return Response.sendResponse(new ResponseResult<>(422, exception.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> handleArgumentsException(IllegalArgumentException exception) {
        return Response.sendResponse(new ResponseResult<>(422, exception.getMessage()));
    }
}
