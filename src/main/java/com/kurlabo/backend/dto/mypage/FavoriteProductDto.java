package com.kurlabo.backend.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FavoriteProductDto {
    private Long product_id;
    private String list_image_url;
    private String name;
    private int original_price;
    private int discounted_price;
}
