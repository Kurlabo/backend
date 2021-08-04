package com.kurlabo.backend.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPwChangeDto {
    private Long member_id;
    private String insertChangePw;
}
