package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/mypage")
public class MypageController {

    @GetMapping("/mypage_orderlist")
    public List<OrderListDto> orderListTest(){
        List<OrderListDto> dummyDtoList = new ArrayList<>();

        OrderListDto orderListDto1 = new OrderListDto(
                "2020.07.13(18시 32분)",
                "[코시] 호주산 펫밀크 1L",
                Long.parseLong("1594632706623"),
                6300,
                "배송완료",
                "https://img-cf.kurly.com/shop/data/goods/1562303711815s0.jpg"
        );
        OrderListDto orderListDto2 = new OrderListDto(
                "2021.01.16(20시 05분)",
                "[선물세트] 서울약사신협 석류즙 30포",
                Long.parseLong("3842536821567"),
                15920,
                "배송중",
                "https://img-cf.kurly.com/shop/data/goods/1587357028431s0.jpg"
        );

        dummyDtoList.add(orderListDto1);
        dummyDtoList.add(orderListDto2);

        return dummyDtoList;
    }

    @GetMapping("/mypage_orderview")
    public OrderDetailDto orderDetailTest(@RequestParam Long ordno){
        OrderDetailDto dummyDto = new OrderDetailDto();
        List<OrderedProductsDto> orderedProductsDtoList = new ArrayList<>();

        OrderedProductsDto orderedProductsDto1 = new OrderedProductsDto(
                "[코시] 호주산 펫밀크 1L",
                (6300*5),
                5,
                "배송완료"
        );
        OrderedProductsDto orderedProductsDto2 = new OrderedProductsDto(
                "절단 셀러리 500g",
                (2990*10),
                10,
                "배송완료"
        );

        orderedProductsDtoList.add(orderedProductsDto1);
        orderedProductsDtoList.add(orderedProductsDto2);

        dummyDto.setOrder_id(ordno);
        dummyDto.setOrderedProductsDtoList(orderedProductsDtoList);
        dummyDto.setCheckout_total_price(orderedProductsDto1.getCheckout_price() + orderedProductsDto2.getCheckout_price());
        dummyDto.setCheckout_method("신용카드");
        dummyDto.setOrderer_name("박상언");
        dummyDto.setSender_name("박상언");
        dummyDto.setCheckout_date("2021-02-06 02:55:00");
        dummyDto.setReciever_name("임정우");
        dummyDto.setReciever_phone("010-4321-5678");
        dummyDto.setReciever_address("(05123) 서울시 성동구 성동로 32 패스트캠퍼스 8층 C강의장");
        dummyDto.setReciever_recieve_place("문 앞");

        return dummyDto;
    }

    @GetMapping("/mypage_wishlist")
    public List<WishListDto> wishListTest(){
        List<WishListDto> wishListDtoList = new ArrayList<>();

        WishListDto dummyDto1 = new WishListDto();

        dummyDto1.setName("절단 셀러리 500g");
        dummyDto1.setDiscounted_price(2990);
        dummyDto1.setList_image_url("https://img-cf.kurly.com/shop/data/goods/1584515163199s0.jpg");

        wishListDtoList.add(dummyDto1);

        WishListDto dummyDto2 = new WishListDto();

        dummyDto2.setName("[락앤락] 숨쉬는 발효숙성 용기 세트");
        dummyDto2.setDiscounted_price(27965);
        dummyDto2.setList_image_url("https://img-cf.kurly.com/shop/data/goods/1599797405749s0.jpg");

        wishListDtoList.add(dummyDto2);

        return wishListDtoList;
    }

    @GetMapping("/mypage_qna")
    public QnaDto qnaTest(){
        String[] dummyStr = {
            "배송지연/불만","컬리패스 (무료배송)", "반품문의", "A/S문의", "환불문의", "주문결제문의", "회원정보문의", "취소문의", "교환문의", "상품정보문의", "기타문의"
        };
        Long[] dummyLong = {Long.parseLong("1945327660572"), Long.parseLong("3484593475423")};
        return new QnaDto(dummyStr, dummyLong, "noah@fastcampus.com", "010-4321-5678");
    }
}
