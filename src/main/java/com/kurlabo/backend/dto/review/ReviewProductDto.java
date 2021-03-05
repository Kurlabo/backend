package com.kurlabo.backend.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewProductDto {
    private Long product_id;

    private String name;

    private int checkout_price;

    private int cnt;
}
