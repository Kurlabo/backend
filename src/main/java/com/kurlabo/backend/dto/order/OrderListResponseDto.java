package com.kurlabo.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListResponseDto {
    private LocalDate checkout_date;
    private String product_name;
    private int else_product_cnt;
    private String list_image_url;
    private Long order_id;
    private int total_price;
    private String delivery_condition;
}
