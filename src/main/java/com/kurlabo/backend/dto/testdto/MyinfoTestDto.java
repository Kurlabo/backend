package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyinfoTestDto {
    private String uid;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String gender;
    private String date_of_birth;
}
