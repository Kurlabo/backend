package com.kurlabo.backend.dto.main;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Builder
public class MainPageProductDto {
    @NotNull
    private Long product_id;

    private String detail_image_url;
    private String sticker_image_url;
    private String name;
    private int original_price;
    private int discounted_price;
    private int discount_percent;

    @QueryProjection
    public MainPageProductDto(Long product_id, String detail_image_url, String sticker_image_url, String name, int original_price, int discount_percent, int discounted_price){
        this.product_id = product_id;
        this.detail_image_url = detail_image_url;
        this.sticker_image_url = sticker_image_url;
        this.name = name;
        this.original_price = original_price;
        this.discount_percent = discount_percent;
        this.discounted_price = discounted_price;
    }
}
