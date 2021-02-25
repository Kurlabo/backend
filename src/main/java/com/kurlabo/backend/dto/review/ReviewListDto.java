package com.kurlabo.backend.dto.review;

import com.kurlabo.backend.model.Review;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewListDto {

    private Long order_id;

    private Long product_id;

    private String product_name;

    private String title;

    private String content;

    private Long help;

    private LocalDate regdate;

    private String delivery_condition;

    private int cnt;

    private String main_img_url;
}
