package com.kurlabo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String name;    // 상품 제목
//    private String short_description;// 간단설명
//    private boolean is_sales;   // 세일중?
//    private String unit_text;   // 판매단위
//    private String weight;      //중량/용량
//    private String origin;      //원산지
//    private String contactant;  // 알레르기 정보
//    private String brand_title; // 브랜드 이름
//    private String expiration_date;// 유통기한
//    private String delivery_time_type_text;//배송구분
//    private String packing_type_text;//포장타입
//    private int original_price;//원가격
//    private int discounted_price;// 할인가격
//    private int discount_percent;//할인율
//    private String discount_end_datetime;//할인 종료 시간
//    private String original_image_url;// 상품 이미지
//    private String main_image_url;//상품 이미지
//    private String list_image_url;// 상품 이미지
//    private String detail_image_url;// 상품 이밎
//    private String sticker_image_url;// 세일 스티커 이미지
}
