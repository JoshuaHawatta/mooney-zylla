package com.joshuahawatta.moneyzilla.helpers.responses;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public final class ResponseResultWIthMessage<T> extends Response<T> {
    private String message;

    public ResponseResultWIthMessage(Integer statusCode, T results, String message) {
        super(statusCode, results);
        this.message = message;
    }
}
