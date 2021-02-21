package com.kurlabo.backend.dto.review;

import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
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
public class ReviewDto {
    private Long review_id;

    private Long member_id;

    private Long product_id;

    private String title;

    private String content;

    private String writer;

    private LocalDate regdate;

    private Long help;

    private Long cnt;
}
