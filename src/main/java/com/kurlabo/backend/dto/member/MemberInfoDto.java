package com.kurlabo.backend.dto.member;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class MemberInfoDto {
    private String uid;
    private String name;
    private String phone;
    private String email;
    private String gender;
    private LocalDate date_of_birth;
    private int check_term;
    private int check_sns;
    @Builder
    public MemberInfoDto(String uid, String name, String phone, String email, String gender, LocalDate date_of_birth, int check_term, int check_sns) {
        this.uid = uid;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.date_of_birth = date_of_birth;
        this.check_term = check_term;
        this.check_sns = check_sns;
    }
}
