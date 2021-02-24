package com.kurlabo.backend.exception;

import org.springframework.http.HttpStatus;

public class DataNotFoundException extends BaseException {
    public DataNotFoundException(String message) {

        super(message, HttpStatus.BAD_REQUEST);
    }
}
