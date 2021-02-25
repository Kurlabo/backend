package com.kurlabo.backend.dto.review;

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
public class ReviewProductDto {
    private Long product_id;

    // private String main_image_url;

    private String name;

    private int checkout_price;

    private int cnt;
}
