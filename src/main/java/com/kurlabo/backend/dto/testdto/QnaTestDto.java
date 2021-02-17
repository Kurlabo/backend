package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaTestDto {
    private String[] inquiry_tag;
    private Long order_id[];
    private String email;
    private String phone;
}
