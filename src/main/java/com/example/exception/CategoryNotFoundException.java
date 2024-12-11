package com.example.exception;

import org.springframework.http.HttpStatus;

public class CategoryNotFoundException extends RuntimeException implements BaseHttpException {
    public CategoryNotFoundException(String message) {
        super(message);
    }

    @Override
    public HttpStatus getHttpStatus() {
        return HttpStatus.NOT_FOUND;
    }
}

