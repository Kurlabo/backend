package com.kurlabo.backend.memberjoin.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiRequest {

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
}
