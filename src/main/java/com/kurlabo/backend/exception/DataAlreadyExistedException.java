package com.kurlabo.backend.exception;

import org.springframework.http.HttpStatus;

public class DataAlreadyExistedException extends BaseException {
    public DataAlreadyExistedException(String message) {
        super(message, HttpStatus.BAD_REQUEST);}
}

