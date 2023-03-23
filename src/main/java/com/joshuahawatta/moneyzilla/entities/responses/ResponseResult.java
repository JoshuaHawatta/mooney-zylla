package com.joshuahawatta.moneyzilla.entities.responses;

public final class ResponseResult<T> extends Response<T> {
    public ResponseResult(Integer statusCode, T result) { super(statusCode, result); }
}
