package com.kurlabo.backend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutProductsDto {
    private Long product_id;
    private String product_name;
    private int product_price;
    private int product_discount_price;
    private int product_cnt;
    private String list_image_url;
    private int review_status;
}
