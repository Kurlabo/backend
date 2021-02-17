package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfoTestDto {
    private Long member_id;
    private String uid;
    private String address;
}
