package com.kurlabo.backend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCartCntRequestDto {
    @NotNull
    private Long product_id;
    @NotNull
    private int variation;
}
