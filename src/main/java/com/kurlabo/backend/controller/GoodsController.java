package com.kurlabo.backend.controller;


import com.kurlabo.backend.dto.testdto.CartTestDto;
import com.kurlabo.backend.dto.testdto.ProductInfoDto;
import com.kurlabo.backend.dto.testdto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/goods")
public class GoodsController {

    @GetMapping("goods_cart")
    public CartTestDto cartTest(){
        CartTestDto dummyDto = new CartTestDto();
        UserInfoDto userInfoDto = new UserInfoDto((long)1, "noah", "서울시 성동구 아차산로 18 (뚝섬역)");
        List<ProductInfoDto> productList = new ArrayList<>();

        ProductInfoDto productInfoDto1 = new ProductInfoDto();
        ProductInfoDto productInfoDto2 = new ProductInfoDto();

        productInfoDto1.setProduct_id((long)1);
        productInfoDto1.setCategory(0);
        productInfoDto1.setName("한끼 당근 1개");
        productInfoDto1.setShort_description("딱 하나만 필요할 때 한끼 당근");
        productInfoDto1.setOriginal_price(1300);
        productInfoDto1.setDiscounted_price(1300);
        productInfoDto1.setOriginal_image_url("https://img-cf.kurly.com/shop/data/goods/1583285919646l0.jpg");
        productInfoDto1.setSticker_image_url("https://img-cf.kurly.com/shop/data/my_icon/icon_farming_coupon_20_percent.png");
        productInfoDto1.setPacking_type_text("냉장/종이포장");

        productList.add(productInfoDto1);

        productInfoDto2.setProduct_id((long)15);
        productInfoDto2.setCategory(5);
        productInfoDto2.setName("컬리플라워 라이스 340g(냉동)");
        productInfoDto2.setShort_description("저칼로리 식단을 위한 이색 라이스 (1팩/340g)");
        productInfoDto2.setOriginal_price(4200);
        productInfoDto2.setDiscounted_price(3780);
        productInfoDto2.setOriginal_image_url("https://img-cf.kurly.com/shop/data/goods/1564118564516l0.jpg");
        productInfoDto2.setSticker_image_url("https://img-cf.kurly.com/shop/data/my_icon/icon_save_10.png");
        productInfoDto2.setPacking_type_text("냉동/종이포장");

        productList.add(productInfoDto2);

        dummyDto.setUserInfoDto(userInfoDto);
        dummyDto.setProductInfoDto(productList);

        return dummyDto;
    }
}
