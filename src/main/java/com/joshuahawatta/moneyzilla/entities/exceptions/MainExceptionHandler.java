package com.joshuahawatta.moneyzilla.entities.exceptions;

import com.joshuahawatta.moneyzilla.entities.responses.Response;
import com.joshuahawatta.moneyzilla.entities.responses.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MainExceptionHandler {
    protected static final String SERVER_ERROR_MESSAGE = "Ops, algo deu errado na nossa parte, tente mais tarde!";

    //NOT_FOUND_HANDLER
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Response<String>> handleNotFound(NullPointerException exception) {
        return Response.sendResponse(new ResponseResult<>(404, exception.getMessage()));
    }

    //DENIED_ACESS_ERROR_HANDLER
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Response<String>> handleServerError(AccessDeniedException exception) {
        return Response.sendResponse(new ResponseResult<>(403, exception.getMessage()));
    }
}
