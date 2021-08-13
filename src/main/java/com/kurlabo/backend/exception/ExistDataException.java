package com.kurlabo.backend.exception;

public class ExistDataException extends RuntimeException {
    public ExistDataException(String message) {
        super(message);
    }
}
