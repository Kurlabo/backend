package com.kurlabo.backend.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCartResponseDto {
    private Long product_id;
    private String data;
    private int cnt;
}
