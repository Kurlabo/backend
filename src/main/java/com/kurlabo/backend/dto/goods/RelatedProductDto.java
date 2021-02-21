package com.kurlabo.backend.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RelatedProductDto {
    private Long product_id;

    private String name;

    private String list_image_url;

    private int original_price;

    private int discounted_price;
}
