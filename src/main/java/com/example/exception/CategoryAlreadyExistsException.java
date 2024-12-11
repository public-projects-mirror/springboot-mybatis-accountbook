package com.example.exception;

import org.springframework.http.HttpStatus;

public class CategoryAlreadyExistsException extends RuntimeException implements BaseHttpException {
    public CategoryAlreadyExistsException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.CONFLICT;
    }
}
