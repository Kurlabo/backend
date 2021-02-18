package com.kurlabo.backend.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCartResponseDto {
    private List<CartDataDto> cartDataDto;
    private String address;
}
