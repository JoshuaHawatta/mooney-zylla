package com.joshuahawatta.moneyzilla.entities.exceptions;

import com.joshuahawatta.moneyzilla.entities.responses.Response;
import com.joshuahawatta.moneyzilla.entities.responses.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerErrorException;

@ControllerAdvice
public class MainExceptionHandler {
    private static final String SERVER_ERROR_MESSAGE = "Ops, algo deu errado na nossa parte, tente mais tarde!";

    //NOT_FOUND_HANDLER
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<Response<String>> handleNotFound(NullPointerException exception) {
        return Response.sendResponse(new ResponseResult<>(404, exception.getMessage()));
    }

    //UNPROCESSABLE_ENTITY_HANDLER
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Response<String>> handleUnprocessableEntity(IllegalArgumentException exception) {
        return Response.sendResponse(new ResponseResult<>(422, exception.getMessage()));
    }

    //SERVER_ERROR_HANDLER
    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Response<String>> handleServerError(ServerErrorException exception) {
        return Response.sendResponse(new ResponseResult<>(500, SERVER_ERROR_MESSAGE));
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Response<String>> handleHttpServerError(HttpServerErrorException exception) {
        return Response.sendResponse(new ResponseResult<>(500, SERVER_ERROR_MESSAGE));
    }

    //UNKNOWN_ERROR
    @ExceptionHandler(UnknownError.class)
    public ResponseEntity<Response<String>> handleUnknownError(UnknownError exception) {
        return Response.sendResponse(new ResponseResult<>(520, SERVER_ERROR_MESSAGE));
    }
}
