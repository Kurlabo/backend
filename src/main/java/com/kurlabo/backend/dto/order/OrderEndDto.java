package com.kurlabo.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderEndDto {
    private Long orders_id;
    private int total_price;
    private String orderer;
    private String checkout;
}
