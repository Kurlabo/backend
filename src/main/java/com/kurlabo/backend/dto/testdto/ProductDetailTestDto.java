package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailTestDto {
    private Long productDetailId;

    @JoinColumn(name = "product_id")
    private ProductInfoTestDto productInfoTestDto;
//    private Long productId;

    private String detailImgUrl;

    private String detailTitle;

    private String detailContext;

    private String productImgUrl;
}
