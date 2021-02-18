package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderedProductsTestDto {
    private String product_name;
    private int checkout_price;
    private int cnt;
    private String deliver_condition;
}
