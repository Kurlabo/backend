package com.kurlabo.backend.dto.goods;

import com.kurlabo.backend.dto.review.ReviewDto;
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
    private Long product_id;

    // private String data;

    private String name;

    private String short_description;

    private String unit_text;

    private String weight;

    private String origin;

    private String contactant;

    private String brand_title;

    private String expiration_date; // 유통기한

    private String delivery_time_type_text;

    private String packing_type_text; //포장타입

    private int original_price;

    private int discounted_price;

    private int discount_percent;

    private String discount_end_datetime;

    private String original_image_url;

    private String main_image_url;

    private String list_image_url;

    private String detail_img_url; // 상품설명 대표 이미지

    private String sticker_image_url;

    private String detail_context;// 상품설명 내용

    private String detail_title;// 상품설명 제목

    private String product_img_url;// 상품이미지 대표 이미지

    private List<String> guides;

    private List<ReviewDto> reviews;

    private List<RelatedProductDto> related_product;

}