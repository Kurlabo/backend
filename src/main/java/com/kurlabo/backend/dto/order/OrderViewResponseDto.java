package com.kurlabo.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderViewResponseDto {
    private List<OrderProductDto> orderProducts;
    private String delivery_condition;
    private int checkout_total_price;
    private String checkout_method;
    private String orderer_name;
    private String sender_name;
    private LocalDate checkout_date;
    private String reciever_name;
    private String reciever_phone;
    private String reciever_address;
    private String reciever_place;
    private String reciever_visit_method;
    private String arrived_alarm;
}
