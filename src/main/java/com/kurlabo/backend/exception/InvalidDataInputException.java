package com.kurlabo.backend.exception;

import org.springframework.http.HttpStatus;

public class InvalidDataInputException extends BaseException {
    public InvalidDataInputException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
