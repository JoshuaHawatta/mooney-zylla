package com.joshuahawatta.moneyzilla.entities.responses;

/**
 * One of the heir classes of Response. Most used for sending just the status code and a result.
 * @param <T>
 */
public final class ResponseResult<T> extends Response<T> {
    public ResponseResult(Integer statusCode, T result) { super(statusCode, result); }
}
