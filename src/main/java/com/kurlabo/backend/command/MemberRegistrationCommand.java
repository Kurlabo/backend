package com.kurlabo.backend.command;

import com.kurlabo.backend.model.Member;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;
import java.time.LocalDate;

import static com.kurlabo.backend.service.MemberSecurityService.DEFAULT_USER_ROLE;

@Getter
@Setter
public class MemberRegistrationCommand {
    @NotNull
    @Size(min = 6, max = 16, message = "6자 이상의 영문 혹은 영문과 숫자를 조합하여 입력해주세요")
    private String uid;

    @NotNull
    @Pattern(regexp="^((?=.*[a-zA-Z])(?=.*\\d)|(?=.*\\d)(?=.*[@$!%*?&])|((?=.*[a-zA-Z])(?=.*[@$!%*?&])))[A-Za-z\\d@$!%*?&]{8,17}$",
            message = "8자리에서 16자리의 영문/숫자/특수문자(공백 제외)만 허용하며, 2개 이상 조합하여 입력해주세요")
    private String password;

    @NotBlank
    //    @Pattern(regexp="^[a-z]*$, ^[가-힣]*$", message = "이름을 입력해주세요")
    private String name;

    private String gender;

    @NotBlank
    @Email(message = "올바른 이메일 형식이 아닙니다")
    private String email;

    @NotBlank
    @Size(min = 11, max = 13, message = "010으로 시작하는 -를 포함하거나 포함하지 않는 값으로 전달해주세요")
    @Pattern(regexp="^010((-\\d{4}-\\d{4})|(\\d{8}))$")
    private String phone;

    private LocalDate date_of_birth;

    public Member toMember(PasswordEncoder passwordEncoder) {

        this.gender = this.gender != null && this.gender.isEmpty() ? null : this.gender;

        return Member.builder()
                .uid(this.uid)
                .email(this.email)
                .password(passwordEncoder.encode(this.password))
                .name(this.name)
                .phone(this.phone.replaceAll("-", ""))
                .gender(this.gender)
                .date_of_birth(this.date_of_birth)
                .grade(Member.DEFAULT_GRADE)
                .role(DEFAULT_USER_ROLE)
                .build();
    }
}
