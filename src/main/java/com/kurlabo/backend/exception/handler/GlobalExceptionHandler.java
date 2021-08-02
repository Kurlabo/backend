package com.kurlabo.backend.exception.handler;

import com.kurlabo.backend.dto.exception.ErrorDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(ResourceNotFoundException.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ErrorDto

}
