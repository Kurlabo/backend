package com.kurlabo.backend.dto.goods;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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

    @QueryProjection
    public GoodsListResponseDto(Long product_id, String original_image_url, String sticker_image_url, String name, int original_price, int discounted_price, int discount_percent, String short_description){
        this.product_id = product_id;
        this.original_image_url = original_image_url;
        this.sticker_image_url = sticker_image_url;
        this.name = name;
        this.original_price = original_price;
        this.discounted_price = discounted_price;
        this.discount_percent = discount_percent;
        this.short_description = short_description;
    }
}
