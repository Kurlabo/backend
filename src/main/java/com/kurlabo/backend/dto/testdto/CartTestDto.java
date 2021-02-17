package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartTestDto {
    List<ProductInfoTestDto> productInfoTestDto;
    UserInfoTestDto userInfoTestDto;
}
