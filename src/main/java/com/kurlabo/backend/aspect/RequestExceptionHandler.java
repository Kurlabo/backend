package com.kurlabo.backend.aspect;

import com.kurlabo.backend.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@ControllerAdvice(basePackages = {"com.kurlabo.backend.controller"})
public class RequestExceptionHandler extends ResponseEntityExceptionHandler {

    private HttpServletRequest givenRequest() {
        return ((ServletRequestAttributes) RequestContextHolder
                .currentRequestAttributes())
                .getRequest();
    }

    private URI getRequestURI() {
        return URI.create(givenRequest().getRequestURI());
    }

    private ResponseEntity<Object> makeResponse(String exceptionMessage, HttpStatus httpStatus) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", exceptionMessage);
        if (httpStatus == null) {
            // todo: which one?
            httpStatus = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .location(getRequestURI())
                .body(responseBody);
    }

    private ResponseEntity<Object> makeResponse(BaseException e) {
        return makeResponse(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler
    public ResponseEntity<?> handleBaseException(BaseException e) {
        return makeResponse(e);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return makeResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
