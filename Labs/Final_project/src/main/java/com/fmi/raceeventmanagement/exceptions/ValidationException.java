package com.fmi.raceeventmanagement.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException {
    private final String errorKey;

    public ValidationException(String message) {
        super(message);
        errorKey = "error";
    }

    public ValidationException(String message, String errorKey) {
        super(message);
        this.errorKey = errorKey;
    }
}
