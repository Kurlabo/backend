package com.kurlabo.backend.dto.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WritableReviewListDto {

    private Long order_id;

    private List<ReviewDto> writableReviewList;
}
