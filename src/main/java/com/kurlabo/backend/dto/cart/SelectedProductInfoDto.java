package com.kurlabo.backend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectedProductInfoDto {
    private List<CheckoutProductsDto> checkout_Products;
    private int total_discounted_price;
}
