package com.kurlabo.backend.dto.order;

import com.kurlabo.backend.dto.cart.CheckoutProductsDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSheetResponseDto {
    @NotNull
    private Long orders_id;
    @NotNull
    private String orderer_name;
    @NotNull
    private String orderer_phone;
    @NotNull
    private String orderer_email;
    @NotNull
    private String orderer_address;
    @NotNull
    private List<CheckoutProductsDto> products_list;
    @NotNull
    private int total_price;
    @NotNull
    private int total_discounted_price;
}
