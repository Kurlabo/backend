package com.kurlabo.backend.exception;

public class LoginFailException extends BaseException {

    private static final String MESSAGE = "아디이나 페스워드가 일치하지 않습니다.";

    public LoginFailException() {
        super(MESSAGE);
    }

    public LoginFailException(String message) {
        super(message);
    }
}
