package com.joshuahawatta.moneyzilla.helpers.responses;

public final class ResponseResult<T> extends Response<T> {
    public ResponseResult(Integer statusCode, T result) { super(statusCode, result); }
}
