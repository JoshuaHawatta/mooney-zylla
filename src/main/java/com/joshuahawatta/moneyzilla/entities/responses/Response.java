package com.joshuahawatta.moneyzilla.entities.responses;

import org.springframework.http.ResponseEntity;

public abstract sealed class Response<T> permits ResponseResult, ResponseResultWIthMessage {
    protected Integer statusCode;
    protected T result;

    //CONSTRUCTOR
    protected Response(Integer statusCode, T result) {
        this.statusCode = statusCode;
        this.result = result;
    }

    public static <T> ResponseEntity<Response<T>> sendResponse(Response<T> response) {
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    //GETTERS_AND_SETTERS
    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}