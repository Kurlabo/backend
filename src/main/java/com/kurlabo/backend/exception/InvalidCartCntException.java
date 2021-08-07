package com.kurlabo.backend.exception;

public class InvalidCartCntException extends RuntimeException{
    public InvalidCartCntException(String message) {
        super(message);
    }
}
