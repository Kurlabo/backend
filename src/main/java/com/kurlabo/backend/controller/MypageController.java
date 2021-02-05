package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.OrderDetailDto;
import com.kurlabo.backend.dto.testdto.OrderListDto;
import com.kurlabo.backend.dto.testdto.OrderedProductsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/shop/mypage")
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
        OrderDetailDto orderDetailDto = new OrderDetailDto();
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

        orderDetailDto.setOrder_id(ordno);
        orderDetailDto.setOrderedProductsDtoList(orderedProductsDtoList);
        orderDetailDto.setCheckout_total_price(orderedProductsDto1.getCheckout_price() + orderedProductsDto2.getCheckout_price());
        orderDetailDto.setCheckout_method("신용카드");
        orderDetailDto.setOrderer_name("박상언");
        orderDetailDto.setSender_name("박상언");
        orderDetailDto.setCheckout_date("2021-02-06 02:55:00");
        orderDetailDto.setReciever_name("임정우");
        orderDetailDto.setReciever_phone("010-4321-5678");
        orderDetailDto.setReciever_address("(05123) 서울시 성동구 성동로 32 패스트캠퍼스 8층 C강의장");
        orderDetailDto.setReciever_recieve_place("문 앞");

        return orderDetailDto;
    }
}
