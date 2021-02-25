package com.kurlabo.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

public class CommandValidationFailedException extends BaseException {

    public CommandValidationFailedException(Errors errors) {
        super(buildErrorString(errors), HttpStatus.BAD_REQUEST);
    }

    private static String buildErrorString(Errors errors) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%d개 항목에서 오류가 발생했습니다. ", errors.getErrorCount()));

        stringBuilder.append("[");

        for (int i = 0; i < errors.getFieldErrors().size(); i++) {

            FieldError fieldError = errors.getFieldErrors().get(i);

            stringBuilder.append(i + 1)
                    .append(":")
                    .append(fieldError.getField())
                    .append("(")
                    .append(fieldError.getDefaultMessage())
                    .append(". 제공된 값: ")
                    .append(fieldError.getRejectedValue())
                    .append(")");

            if (i < errors.getFieldErrors().size() - 1) {
                stringBuilder.append(", ");
            }
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
