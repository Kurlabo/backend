package com.kurlabo.backend.dto.cart;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class DeleteCartResponseDto {
    private List<Long> deleted_product_id;
}
