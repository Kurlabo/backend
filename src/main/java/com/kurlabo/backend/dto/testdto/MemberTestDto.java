package com.kurlabo.backend.dto.testdto;

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
public class MemberTestDto {
    private Long memberId;

    private String uid;

    private String password;

    private String name;

    private String email;

    private String phone;

    private String address;

    private String gender;

    private String dateOfBirth;
}
