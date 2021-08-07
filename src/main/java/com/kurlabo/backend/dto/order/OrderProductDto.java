package com.kurlabo.backend.dto.order;

import com.kurlabo.backend.model.Order_Sheet_Products;
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

    public OrderProductDto(Order_Sheet_Products orderSheetProducts) {
        this.product_id = orderSheetProducts.getProduct_id();
        this.list_image_url = orderSheetProducts.getList_image_url();
        this.name = orderSheetProducts.getProduct_name();
        this.checkout_price = orderSheetProducts.getProduct_price();
        this.reduced_price = orderSheetProducts.getDiscounted_price();
        this.cnt = orderSheetProducts.getProduct_cnt();
    }
}
