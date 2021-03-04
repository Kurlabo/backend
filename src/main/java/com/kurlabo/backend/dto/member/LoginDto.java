package com.kurlabo.backend.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotEmpty(message = "아이디를 입력해주세요")
    @Size(min =6, max = 16, message = "아이디는 6글자 이상 16글자 이하로 입력해주세요.")
    private String uid;
    @NotEmpty(message = "비밀번호를 입력해주세요")
    @Size(min =10, max = 16, message = "비밀번호는 10글자 이상 16글자 이하로 입력해주세요.")
    private String password;
}