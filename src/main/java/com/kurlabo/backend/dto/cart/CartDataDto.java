package com.kurlabo.backend.dto.cart;

import lombok.Builder;

@Builder
public class CartDataDto {
    private Long product_id;
    private String name;
    private int original_price;
    private int discounted_price;
    private String packing_type_text;
    private int min_ea;
    private int max_ea;
    private String list_image_url;
    private int cnt;
    private int reduced_price;
}
