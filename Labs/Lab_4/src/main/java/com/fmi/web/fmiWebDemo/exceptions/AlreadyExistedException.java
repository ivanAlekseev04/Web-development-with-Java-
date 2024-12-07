package com.fmi.web.fmiWebDemo.exceptions;

public class AlreadyExistedException extends RuntimeException {
    public AlreadyExistedException(String message) {
        super(message);
    }
}
