package com.joshuahawatta.moneyzilla.entities.responses;

/**
 * One of the heir classes of Response. Used for not only sending results and status code, but a custom message too.
 * @param <T>
 */
public final class ResponseResultWIthMessage<T> extends Response<T> {
    private String message;

    /**
     * Constructors overloads.
     */
    public ResponseResultWIthMessage(Integer statusCode, T results) { super(statusCode, results); }

    /**
     * @param message new parameter on another constructor for adding message for the class.
     */
    public ResponseResultWIthMessage(Integer statusCode, T results, String message) {
        super(statusCode, results);
        this.message = message;
    }

    /**
     * Getters and setters.
     */
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
