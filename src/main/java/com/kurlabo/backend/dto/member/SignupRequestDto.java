package com.kurlabo.backend.dto.member;

import com.kurlabo.backend.dto.MemberDto;
import com.kurlabo.backend.exception.DataAlreadyExistedException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Map;

import static com.kurlabo.backend.service.MemberSecurityService.DEFAULT_USER_ROLE;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequestDto {

    private String uid;
    private String email;
    private String password;
    private String name;
    private String phone;
    private String gender;
    private LocalDate date_of_birth;
    private String grade;
    private String role;

    public void setDefaultRoleAndGrade() {
        this.role = DEFAULT_USER_ROLE;
        this.grade = Member.DEFAULT_GRADE;
    }

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .uid(this.getUid())
                .email(this.getEmail())
                .password(passwordEncoder.encode(this.getPassword()))
                .name(this.getName())
                .phone(this.getPhone())
                .gender(this.getGender())
                .date_of_birth(this.getDate_of_birth())
                .grade(this.getGrade())
                .role(this.getRole())
                .build();
    }
}
