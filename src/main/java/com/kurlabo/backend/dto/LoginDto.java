package com.kurlabo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotEmpty(message = "아이디를 입력해주세요")
    private String uid;

    @NotEmpty(message = "비밀번호를 입력해주세요")
    private String password;
}
