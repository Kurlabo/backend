package com.kurlabo.backend.command;

import com.kurlabo.backend.model.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class MemberUpdateCommand {
    @Email
    @NotBlank
    private String email;

    @Pattern(regexp="(^((?=.*[a-zA-Z])(?=.*\\d)|(?=.*\\d)(?=.*[@$!%*?&])|((?=.*[a-zA-Z])(?=.*[@$!%*?&])))[A-Za-z\\d@$!%*?&]{8,17}$)|(^$)",
            message = "8자리에서 16자리의 영문/숫자/특수문자(공백 제외)만 허용하며, 2개 이상 조합하여 입력해주세요")
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;
    private String gender;
    private LocalDate date_of_birth;

    public void updateMember(Member member) {
        member.setEmail(email);
        member.setName(name);
        if (password != null && !password.isEmpty()) {
            member.setPassword(password);
        }
        member.setPhone(phone);
        member.setGender(gender);

        if (date_of_birth != null) {
            member.setDate_of_birth(date_of_birth);
        }
    }
}
