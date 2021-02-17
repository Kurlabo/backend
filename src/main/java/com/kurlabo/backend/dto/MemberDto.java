package com.kurlabo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDto {
    private Long memberId;

    private String uid;

    private String password;

    private String name;

    private String email;

    private String phone;

    private String gender;

    private String date_of_birth;
}
