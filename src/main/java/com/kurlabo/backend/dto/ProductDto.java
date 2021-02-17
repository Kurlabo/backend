package com.kurlabo.backend.dto;

import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private Long product_id;

    private String data;

    private List<Review> reviews;

    public ProductDto (Product product) {
        this.product_id = product.getId();
        this.data = product.getData();
    }
}
