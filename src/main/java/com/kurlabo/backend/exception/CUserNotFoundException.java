package com.kurlabo.backend.exception;

public class CUserNotFoundException extends RuntimeException{

    public CUserNotFoundException() {
    }

    public CUserNotFoundException(String message) {
        super(message);
    }
}
