package com.joshuahawatta.moneyzilla.helpers.exceptions;

import com.joshuahawatta.moneyzilla.helpers.responses.Response;
import com.joshuahawatta.moneyzilla.helpers.responses.ResponseResult;
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
