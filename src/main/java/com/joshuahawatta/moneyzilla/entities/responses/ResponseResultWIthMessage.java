package com.joshuahawatta.moneyzilla.entities.responses;

public final class ResponseResultWIthMessage<T> extends Response<T> {
    private String message;

    //CONSTRUCTORS_OVERLOADS
    public ResponseResultWIthMessage(Integer statusCode, T results) { super(statusCode, results); }

    public ResponseResultWIthMessage(Integer statusCode, T results, String message) {
        super(statusCode, results);
        this.message = message;
    }

    //GETTERS_AND_SETTERS
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
