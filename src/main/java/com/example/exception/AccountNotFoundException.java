package com.example.exception;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends RuntimeException implements BaseHttpException {
    public AccountNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
