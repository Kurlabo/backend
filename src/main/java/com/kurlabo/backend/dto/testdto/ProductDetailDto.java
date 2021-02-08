package com.kurlabo.backend.dto.testdto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailDto {
    private Long productDetailId;

    @JoinColumn(name = "product_id")
    private ProductInfoDto productInfoDto;
//    private Long productId;

    private String detailImgUrl;

    private String detailTitle;

    private String detailContext;

    private String productImgUrl;
}
