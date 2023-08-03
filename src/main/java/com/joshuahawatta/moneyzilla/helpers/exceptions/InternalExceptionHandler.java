package com.joshuahawatta.moneyzilla.helpers.exceptions;

import com.joshuahawatta.moneyzilla.helpers.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ServerErrorException;
import java.sql.SQLException;
import java.util.Map;

@ControllerAdvice
public class InternalExceptionHandler {
    private static final String SERVER_ERROR_MESSAGE = "Ops, algo deu errado na nossa parte, tente mais tarde!";

    @ExceptionHandler(ServerErrorException.class)
    public ResponseEntity<Map<String, String>> handleServerException(ServerErrorException exception) {
        return new ResponseEntity<>(Message.asJson(SERVER_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpServerErrorException.class)
    public ResponseEntity<Map<String, String>> handleHttpException(HttpServerErrorException exception) {
        return new ResponseEntity<>(Message.asJson(SERVER_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Map<String, String>> handleSQLException(SQLException exception) {
        return new ResponseEntity<>(Message.asJson(SERVER_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(UnknownError.class)
    public ResponseEntity<Map<String, String>>handleUnknownException(UnknownError exception) {
        return new ResponseEntity<>(Message.asJson(SERVER_ERROR_MESSAGE), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
