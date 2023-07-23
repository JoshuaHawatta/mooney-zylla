package com.joshuahawatta.moneyzilla.entities.responses;

import org.springframework.http.ResponseEntity;

/**
 * An abstract class for handling ResponseEntity<TypeHere> returns.
 * @param <T> for handling multiple return cases
 */
public abstract class Response<T> {
    protected Integer statusCode;
    protected T result;

    /**
     * @param statusCode so a ResponseEntity always have a status code with it
     * @param result the result that can be anything, such as primitive types, classes or even Exceptions.
     */
    protected Response(Integer statusCode, T result) {
        this.statusCode = statusCode;
        this.result = result;
    }

    /**
     * @param response instance a heir class such as ResponseResult with the required data.
     * @@implNote Response.sendResponse(new ResponseResult<String>(200, "Hello, world!")).
     * @return a ResponseEntity<Response<T>, will send a ResponseEntity with the status and result of the heir class.
     * @param <T>
     */
    public static <T> ResponseEntity<Response<T>> sendResponse(Response<T> response) {
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    /**
     * Getters and setters.
     */
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