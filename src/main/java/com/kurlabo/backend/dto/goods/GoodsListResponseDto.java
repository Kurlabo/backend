package com.kurlabo.backend.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsListResponseDto {
    private Long product_id;
    private String original_image_url;
    private String sticker_image_url;
    private String name;
    private int original_price;
    private int discounted_price;
    private int discount_percent;
    private String short_description;
}
