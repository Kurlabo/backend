package com.kurlabo.backend.dto.cart;

import com.kurlabo.backend.model.Order_Sheet_Products;
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
    private int discount_price;
    private int product_cnt;
    private String list_image_url;
    private int review_status;

    public CheckoutProductsDto(Order_Sheet_Products orderSheet) {
        this.product_id = orderSheet.getProduct_id();
        this.product_name = orderSheet.getProduct_name();
        this.product_price = orderSheet.getProduct_price();
        this.discount_price = orderSheet.getDiscounted_price();
        this.product_cnt = orderSheet.getProduct_cnt();
        this.list_image_url = orderSheet.getList_image_url();
        this.review_status = orderSheet.getReview_status();
    }
}
