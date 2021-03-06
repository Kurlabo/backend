package com.kurlabo.backend.dto.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainPageProductDto {
    @NotNull
    private Long product_id;
    private String product_img;
    private String sticker_img;
    private String product_name;
    private int original_price;
    private int discounted_price;
    private int discount_rate;
}
