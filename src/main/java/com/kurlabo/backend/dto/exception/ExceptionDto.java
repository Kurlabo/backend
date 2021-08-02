package com.kurlabo.backend.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ExceptionDto {
    private int code;
    private String message;
}
