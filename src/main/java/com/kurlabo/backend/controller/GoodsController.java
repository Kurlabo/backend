package com.kurlabo.backend.controller;


import com.kurlabo.backend.dto.testdto.CartTestDto;
import com.kurlabo.backend.dto.testdto.ProductInfoDto;
import com.kurlabo.backend.dto.testdto.UserInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

        System.out.println(dummyDto);

        return dummyDto;
    }


    // 상품 상세
    @GetMapping("/{id}")
    public ProductInfoDto getProduct(@PathVariable(name = "id") Long id) {
        ProductInfoDto product = new ProductInfoDto();

        product.setDetail_context("간단히 쪄 먹기도 좋고, 다양한 요리와 함께 곁들여 먹기도 좋은 감자는 우리 식탁에 빼놓을 수 없는 식재료지요. 탄수화물은 물론이고 단백질, 비타민C까지 풍부해 마치 곡류와 채소를 동시에 먹은 것과 같은 효과를 줍니다. 컬리는 그때그때 유명산지 감자를 가락시장에서 수급하여 보내드립니다. 포슬포슬한 식감에 고소하고 은은한 단맛이 나 볶음, 구이, 튀김 등 다양하게 요리해서 먹을 수 있어요. 매일 식탁에 올려도 질리지 않는 감자를 컬리에서 간편하게 만나보세요.");
        product.setDetail_title("\\n포슬포슬하고 고소한 맛\\n감자 1kg\\n");
        product.setDetail_image_url("//img-cf.kurly.com/shop/data/goodsview/20180628/gv40000026292_1.jpg");
        product.setProduct_img_url("//img-cf.kurly.com/shop/data/goodsview/20210202/gv10000156055_1.jpg");

        product.setProduct_id(1L);
        product.setCategory(0);
        product.setName("[KF365] 감자 1kg");
        product.setShort_description("믿고 먹을 수 있는 상품을 합리적인 가격에, KF365");
        product.setOriginal_price(2500);
        product.setDiscounted_price(2500);
        product.setDetail_img_url("https://img-cf.kurly.com/shop/data/goods/153017237655m0.jpg");
        product.setOriginal_image_url("https://img-cf.kurly.com/shop/data/goods/1530172373295l0.jpg");

        return product;
    }

}
