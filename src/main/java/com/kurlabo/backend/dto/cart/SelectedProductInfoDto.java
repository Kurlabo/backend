package com.kurlabo.backend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedProductInfoDto {
    @NotNull
    private List<CheckoutProductsDto> checkout_Products;
    @NotNull
    private int total_price;
    @NotNull
    private int total_discounted_price;
}
