package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductInfoDto {
    private Long product_id;
    private String short_description;
    private String name;
    private int original_price;
    private int discounted_price;
    private String original_image_url;
    private String sticker_image_url;
    private int category;
    private String packing_type_text;

    private String detail_image_url;
    private ProductDetailDto productDetailDto;
}
