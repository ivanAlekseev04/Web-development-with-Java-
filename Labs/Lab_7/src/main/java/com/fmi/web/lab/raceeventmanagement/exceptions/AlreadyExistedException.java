package com.fmi.web.lab.raceeventmanagement.exceptions;

public class AlreadyExistedException extends RuntimeException {
    public AlreadyExistedException(String message) {
        super(message);
    }
}
