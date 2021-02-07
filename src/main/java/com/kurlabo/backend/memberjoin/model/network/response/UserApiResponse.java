package com.kurlabo.backend.memberjoin.model.network.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiResponse {


    private Long uid;

    private String password;

    private String name;

    private String email;

    //todo is the membership grade necessary?
    private String grade;

    private String dateOfBirth;

    private String gender;

    private String address;

    private String phoneNumber;

    private LocalDateTime registeredAt;
}
