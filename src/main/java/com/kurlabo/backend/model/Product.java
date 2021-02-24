package com.kurlabo.backend.model;

import com.kurlabo.backend.converter.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private int category;
    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "text")
    private String data;
    private String detail_img_url;// 상품설명 대표 이미지
    private String detail_context;// 상품설명 내용
    private String detail_title;// 상품설명 제목
    private String product_img_url;// 상품이미지 태그 이미지 url

    private String name;    // 상품 제목
    private String short_description;// 간단설명
    private boolean is_sales;   // 세일중?
    private String unit_text;   // 판매단위
    private String weight;      //중량/용량
    private String origin;      //원산지
    private String contactant;  // 알레르기 정보
    private String brand_title; // 브랜드 이름
    private String expiration_date;// 유통기한
    private String guides;
    private String delivery_time_type_text;//배송구분
    private String packing_type_text;//포장타입
    private int original_price;//원가격
    private int discounted_price;// 할인가격
    private int discount_percent;//할인율
    private String discount_end_datetime;//할인 종료 시간
    private String original_image_url;// 상품 이미지
    private String main_image_url;//상품 이미지
    private String list_image_url;// 상품 이미지
    private String detail_image_url;// 상품 이밎
    private String sticker_image_url;// 세일 스티커 이미지
}
