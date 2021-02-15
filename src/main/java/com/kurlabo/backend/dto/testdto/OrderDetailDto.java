package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDto {
    private Long order_id;
    private List<OrderedProductsDto> orderedProductsDtoList;
    private int checkout_total_price;
    private String checkout_method;
    private String orderer_name;
    private String sender_name;
    private String checkout_date;
    private String reciever_name;
    private String reciever_phone;
    private String reciever_address;
    private String reciever_recieve_place;
}
