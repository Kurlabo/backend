package com.kurlabo.backend.dto.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HeaderDto {
    private String grade;
    private String name;
    private int cartCnt;
    private String uid;
}
