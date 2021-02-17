package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindPwdTestDto {
    private String name;
    private String uid;
    private String email;
}
