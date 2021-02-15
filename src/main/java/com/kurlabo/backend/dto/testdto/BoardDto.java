package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDto {
    private Long board_id;

    private String title;

    private String writer;

    private LocalDateTime regdate;

    private Integer cnt;

    private String content;
}
