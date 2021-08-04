package com.kurlabo.backend.exception.handler;

import com.kurlabo.backend.dto.exception.ExceptionDto;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.InvalidCartCntException;
import com.kurlabo.backend.exception.InvalidPasswordException;
import com.kurlabo.backend.exception.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto DataNotFoundExceptionHandler(DataNotFoundException e) {
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidTokenException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto InvalidTokenExceptionHandler(InvalidTokenException e) {
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidCartCntException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto InvalidCartCntExceptionHandler(InvalidCartCntException e) {
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(InvalidPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto InvalidPasswordException(InvalidCartCntException e) {
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }
}
