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

    private List<Product> related_product;
}
