package com.joshuahawatta.moneyzilla.helpers.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;

@Getter @Setter
@AllArgsConstructor
public abstract class Response<T> {
    protected Integer statusCode;
    protected T result;

    public static <T> ResponseEntity<Response<T>> sendResponse(Response<T> response) {
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}