package com.kurlabo.backend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InsertCartDto {
    @NotNull
    private Long product_id;
    @NotNull
    @Min(value = 1)
    private int cnt;
}
