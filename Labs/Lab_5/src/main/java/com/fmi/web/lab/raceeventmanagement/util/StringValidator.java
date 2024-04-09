package com.fmi.web.lab.raceeventmanagement.util;

import org.springframework.stereotype.Component;

@Component
public abstract class StringValidator {
    public static void validate(String toCheck, String varName) {
        if (toCheck == null || toCheck.isEmpty() || toCheck.isBlank()) {
            throw new IllegalArgumentException(String.format("{ %s } can't be null/empty" +
                    "/blank",varName));
        }
    }
}
