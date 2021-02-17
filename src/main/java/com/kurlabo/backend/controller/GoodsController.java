package com.kurlabo.backend.controller;


import com.kurlabo.backend.dto.testdto.CartTestDto;
import com.kurlabo.backend.dto.testdto.ProductDetailTestDto;
import com.kurlabo.backend.dto.testdto.ProductInfoTestDto;
import com.kurlabo.backend.dto.testdto.UserInfoTestDto;
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
        UserInfoTestDto userInfoTestDto = new UserInfoTestDto((long)1, "noah", "서울시 성동구 아차산로 18 (뚝섬역)");
        List<ProductInfoTestDto> productList = new ArrayList<>();

        ProductInfoTestDto productInfoTestDto1 = new ProductInfoTestDto();
        ProductInfoTestDto productInfoTestDto2 = new ProductInfoTestDto();

        productInfoTestDto1.setProduct_id((long)1);
        productInfoTestDto1.setCategory(0);
        productInfoTestDto1.setName("한끼 당근 1개");
        productInfoTestDto1.setShort_description("딱 하나만 필요할 때 한끼 당근");
        productInfoTestDto1.setOriginal_price(1300);
        productInfoTestDto1.setDiscounted_price(1300);
        productInfoTestDto1.setOriginal_image_url("https://img-cf.kurly.com/shop/data/goods/1583285919646l0.jpg");
        productInfoTestDto1.setSticker_image_url("https://img-cf.kurly.com/shop/data/my_icon/icon_farming_coupon_20_percent.png");
        productInfoTestDto1.setPacking_type_text("냉장/종이포장");

        productList.add(productInfoTestDto1);

        productInfoTestDto2.setProduct_id((long)15);
        productInfoTestDto2.setCategory(5);
        productInfoTestDto2.setName("컬리플라워 라이스 340g(냉동)");
        productInfoTestDto2.setShort_description("저칼로리 식단을 위한 이색 라이스 (1팩/340g)");
        productInfoTestDto2.setOriginal_price(4200);
        productInfoTestDto2.setDiscounted_price(3780);
        productInfoTestDto2.setOriginal_image_url("https://img-cf.kurly.com/shop/data/goods/1564118564516l0.jpg");
        productInfoTestDto2.setSticker_image_url("https://img-cf.kurly.com/shop/data/my_icon/icon_save_10.png");
        productInfoTestDto2.setPacking_type_text("냉동/종이포장");

        productList.add(productInfoTestDto2);

        dummyDto.setUserInfoTestDto(userInfoTestDto);
        dummyDto.setProductInfoTestDto(productList);

        System.out.println(dummyDto);

        return dummyDto;
    }


    // 상품 상세
    @GetMapping("/{id}")
    public ProductInfoTestDto getProduct(@PathVariable(name = "id") Long id) {
        ProductInfoTestDto product = new ProductInfoTestDto();
        ProductDetailTestDto productDetail = new ProductDetailTestDto();

        productDetail.setProductDetailId(1L);
        productDetail.setDetailContext("간단히 쪄 먹기도 좋고, 다양한 요리와 함께 곁들여 먹기도 좋은 감자는 우리 식탁에 빼놓을 수 없는 식재료지요. 탄수화물은 물론이고 단백질, 비타민C까지 풍부해 마치 곡류와 채소를 동시에 먹은 것과 같은 효과를 줍니다. 컬리는 그때그때 유명산지 감자를 가락시장에서 수급하여 보내드립니다. 포슬포슬한 식감에 고소하고 은은한 단맛이 나 볶음, 구이, 튀김 등 다양하게 요리해서 먹을 수 있어요. 매일 식탁에 올려도 질리지 않는 감자를 컬리에서 간편하게 만나보세요.");
        productDetail.setDetailTitle("\\n포슬포슬하고 고소한 맛\\n감자 1kg\\n");
        productDetail.setDetailImgUrl("//img-cf.kurly.com/shop/data/goodsview/20180628/gv40000026292_1.jpg");
        productDetail.setProductImgUrl("//img-cf.kurly.com/shop/data/goodsview/20210202/gv10000156055_1.jpg");

        product.setProduct_id(1L);
        product.setProductDetailTestDto(productDetail);
        product.setCategory(0);
        product.setName("[KF365] 감자 1kg");
        product.setShort_description("믿고 먹을 수 있는 상품을 합리적인 가격에, KF365");
        product.setOriginal_price(2500);
        product.setDiscounted_price(2500);
        product.setDetail_image_url("https://img-cf.kurly.com/shop/data/goods/153017237655m0.jpg");
        product.setOriginal_image_url("https://img-cf.kurly.com/shop/data/goods/1530172373295l0.jpg");

        return product;
    }

}
