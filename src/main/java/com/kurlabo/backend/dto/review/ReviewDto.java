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
    private Long review_id;

    private Long product_id;

    private Long member_id;

    private String product_name;

    private String title;

    private String content;

    private String writer;

    private String list_img_url;

    private LocalDate regdate;

    private String delivery_condition;

    private int help;

    private int cnt;

    private boolean isWritten;

    @QueryProjection
    public ReviewDto(Long review_id, Long product_id, String product_name, String title, String content, LocalDate regdate, int help) {
        this.review_id = review_id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.title = title;
        this.content = content;
        this.regdate = regdate;
        this.help = help;
    }
}
