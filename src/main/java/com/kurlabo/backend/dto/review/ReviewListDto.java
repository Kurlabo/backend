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
public class ReviewListDto {
    private Long product_id;

    private String list_image_url;

    private String product_name;

    private int original_price;

    private int discounted_price;

    private List<Long> writableReviews; // 후기 작성 가능

    private List<Long> writtenReviews; // 작성완료 후기
}
