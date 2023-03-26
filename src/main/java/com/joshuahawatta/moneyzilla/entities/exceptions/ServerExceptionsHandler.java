package com.joshuahawatta.moneyzilla.entities.exceptions;

import com.joshuahawatta.moneyzilla.entities.responses.Response;
import com.joshuahawatta.moneyzilla.entities.responses.ResponseResult;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerErrorException;

@ControllerAdvice
public class ServerExceptionsHandler extends MainExceptionHandler {
    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Response<String>> handleServerException(ServerErrorException exception) {
        return Response.sendResponse(new ResponseResult<>(500, SERVER_ERROR_MESSAGE));
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Response<String>> handleHttpException(HttpServerErrorException exception) {
        return Response.sendResponse(new ResponseResult<>(500, SERVER_ERROR_MESSAGE));
    }

    //UNKNOWN_ERROR
    @ExceptionHandler(UnknownError.class)
    public ResponseEntity<Response<String>> handleUnknownException(UnknownError exception) {
        return Response.sendResponse(new ResponseResult<>(520, SERVER_ERROR_MESSAGE));
    }
}
