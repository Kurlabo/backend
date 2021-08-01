package com.kurlabo.backend.dto.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainResponseDto {
    private List<String> slide_img_list;    // 슬라이드 이미지
    private InstaSrcDto instaSrcDto;        // 인스타 소스 Dto (랜딩 url , 썸네일 이미지 url)
    private String setbyul_img;             // 샛별 배송 배너 이미지
    private List<MainPageProductDtoProjection> howAbout;// 이 상품 어때요?
    private List<MainPageProductDtoProjection> frugality;// 알뜰 상품
    private List<MainPageProductDtoProjection> mdRecommend;// MD의 추천
    private List<MainPageProductDtoProjection> todays;// 오늘의 신상품
    private List<MainPageProductDtoProjection> hots;// 지금 가장 핫한 상품
    private List<MainPageProductDtoProjection> goodPrice;// 늘 기분 좋은 가격
    private List<MainPageProductDtoProjection> fastFood;// 뚝딱 간편식
}
