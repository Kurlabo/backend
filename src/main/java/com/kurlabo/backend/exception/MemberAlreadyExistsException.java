package com.kurlabo.backend.exception;

import com.kurlabo.backend.exception.BaseException;
import org.springframework.http.HttpStatus;

public class MemberAlreadyExistsException extends BaseException {
    public MemberAlreadyExistsException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
