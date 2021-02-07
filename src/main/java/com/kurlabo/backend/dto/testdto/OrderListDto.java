package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderListDto {
    private String checkout_date;
    private String product_name;
    private Long order_id;
    private int checkout_price;
    private String deliver_condition;
    private String list_image_url;
}
