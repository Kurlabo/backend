package com.kurlabo.backend.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

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
}
