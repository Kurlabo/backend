package com.kurlabo.backend.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertCartDto {
    @NotNull
    private Long product_id;
    @NotNull
    private int cnt;
}
