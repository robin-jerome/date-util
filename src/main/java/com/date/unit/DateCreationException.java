package com.date.unit;

public class DateCreationException extends RuntimeException {

    public DateCreationException(String message) {
        super(message);
    }

    public DateCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
