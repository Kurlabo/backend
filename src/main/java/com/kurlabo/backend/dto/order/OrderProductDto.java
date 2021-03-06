package com.kurlabo.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderProductDto {
    private Long product_id;
    private String list_image_url;
    private String name;
    private int checkout_price;
    private int reduced_price;
    private int cnt;
}
