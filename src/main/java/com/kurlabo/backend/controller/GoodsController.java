package com.kurlabo.backend.controller;


import com.kurlabo.backend.dto.ReviewDto;
import com.kurlabo.backend.dto.goods.InsertCartDto;
import com.kurlabo.backend.dto.testdto.CartTestDto;
import com.kurlabo.backend.dto.testdto.ProductDetailTestDto;
import com.kurlabo.backend.dto.testdto.ProductInfoTestDto;
import com.kurlabo.backend.dto.testdto.UserInfoTestDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.service.GoodsService;
import com.kurlabo.backend.service.MemberService;
import com.kurlabo.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/goods")
public class GoodsController {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final ReviewService reviewService;

    // 장바구니 상품 추가
    @PostMapping("/goods_cart")
    public void insertCart(@RequestBody @Valid InsertCartDto dto){    // Security에서 member 가져와야함
        Member mem = memberService.findById((long)1);
        goodsService.insertCart(mem, dto.getProduct_id(), dto.getCnt());
    }

    @PostMapping("/goods_cart/delete")


    @PatchMapping("/goods_cart/{cart_id}")


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
}
