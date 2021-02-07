package com.kurlabo.backend.memberjoin.model.network.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserApiRequest {

    private Long id;

    private String uid;

    private String password;

    private String name;

    private String email;

    private String phoneNumber;

    private String address;

    private String gender;

    private String dateOfBirth;

    private String grade;

//    private LocalDateTime registeredAt;
//
//    private LocalDateTime unregisteredAt;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}
