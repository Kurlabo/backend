package com.kurlabo.backend.exception;

public class UserNotFoundException extends BaseException {

    public UserNotFoundException() {
        super("사용자를 찾을 수 없습니다");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
