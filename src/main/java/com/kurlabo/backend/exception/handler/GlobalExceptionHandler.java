package com.kurlabo.backend.exception.handler;

import com.kurlabo.backend.dto.exception.ExceptionDto;
import com.kurlabo.backend.exception.*;
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
    public ExceptionDto InvalidPasswordException(InvalidPasswordException e) {
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ExistDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto ExistDataException(ExistDataException e) {
        return ExceptionDto.builder()
                .message(e.getMessage())
                .build();
    }
}
