package com.kurlabo.backend.dto.review;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewDto {
    private Long orders_id;

    private Long review_id;

    private Long product_id;

    private Long orderSheetProduct_id;

    private Long member_id;

    private String product_name;

    private String title;

    private String content;

    private String writer;

    private String list_img_url;

    private LocalDate regDate;

    private String delivery_condition;

    private int help;

    private int cnt;

    private boolean isWritten;

    @QueryProjection
    public ReviewDto(Long review_id, Long product_id, String product_name, String title, String content, LocalDate regDate, int help) {
        this.review_id = review_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.title = title;
        this.content = content;
        this.regDate = regDate;
        this.help = help;
    }
}
