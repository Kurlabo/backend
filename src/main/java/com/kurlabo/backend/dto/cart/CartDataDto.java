package com.kurlabo.backend.dto.cart;

import com.hazelcast.com.eclipsesource.json.JsonObject;
import com.kurlabo.backend.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDataDto {
    private Long product_id;
    private String data;
    private int cnt;
}
