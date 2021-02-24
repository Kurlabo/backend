package com.kurlabo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long memberId;

    @Size(min = 6, max = 16, message = "6자 이상의 영문 혹은 영문과 숫자를 조")
    private String uid;

    @Size(min = 8, max = 16, message = "10자이상 입력")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "영문/숫자/특수문자(공백 제외)만 허용하며, 2개 이상 조합")
    private String password;


    private String oldPassword;

//    @Pattern(regexp="^[a-z]*$, ^[가-힣]*$", message = "이름을 입력해주세요")
    private String name;

    @Email(message = "이메일 형식이 아닙니")
    private String email;

    @Size(min = 11, max = 11, message = "연락처는 11자리 숫자만 입력해주세요")
    @Pattern(regexp="^[0-9]*$", message = "숫자만 입력해주세요")
    private String phone;

    private String gender;

    private String grade;

    private LocalDate date_of_birth;

    private Boolean isDeleted;
}
