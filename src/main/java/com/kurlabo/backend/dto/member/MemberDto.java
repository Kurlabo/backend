package com.kurlabo.backend.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    @NotEmpty(message = "아이디를 입력해주세요")
    @Size(min =6, max = 16, message = "아이디는 6글자 이상 16글자 이하로 입력해주세요.")
    private String uid;
    @Email(message = "이메일 형식이 아닙니다")
    private String email;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min =10, max = 16, message = "비밀번호는 10글자 이상 16글자 이하로 입력해주세요.")
//    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,}$")
    private String password;
//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min =10, max = 16)
//    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,}$")
    private String CheckPassword;
    @Pattern(regexp="^[가-힣]*$", message = "한글을 입력해주세요")
    private String name;
    @Size (min = 11, max = 11)
    @Pattern(regexp="^[0-9]*$" , message = "숫자로만 입력해주세요")
    private String phone;
    private String gender;
    @Past(message = "생년월일을 다시 입력해주세요.")
    private LocalDate date_of_birth;
    private String address;
    private String detail_address;
    @Range(min = 0, max = 1)
    private int check_term;
    @Range(min = 0, max = 3)
    private int check_sns;
}