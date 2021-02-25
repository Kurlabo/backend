package com.kurlabo.backend.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException() {
        super("requested resource is not found", HttpStatus.BAD_REQUEST);
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
