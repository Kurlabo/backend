package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.mypage.DeleteWishListDto;
import com.kurlabo.backend.dto.mypage.InsertWishListDto;
import com.kurlabo.backend.dto.testdto.*;
import com.kurlabo.backend.model.Favorite;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.service.FavoriteService;
import com.kurlabo.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/mypage")
public class MypageController {

    private final FavoriteService favoriteService;
    private final MemberService memberService;

    // 늘 사는 것 리스트 불러오기
    @GetMapping("/mypage_wishlist")
    public Page<Favorite> getAllWishList(@AuthenticationPrincipal Member member, @PageableDefault(size = 5) Pageable pageable){
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함
        return favoriteService.getFavoriteList(mem, pageable);
    }

    // 늘 사는 것 Insert
    @PostMapping("/mypage_wishlist")
    public String insertWishlist(@RequestBody @Valid InsertWishListDto dto){
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함
        return favoriteService.insertFavorite(mem, dto.getProduct_id());
    }

    // 늘 사는 것 비우기
    @DeleteMapping("/mypage_wishlist")
    public Page<Favorite> deleteWishList (@AuthenticationPrincipal Member member, @RequestBody @NotNull DeleteWishListDto dto, @PageableDefault(size = 5) Pageable pageable) {
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함
        return favoriteService.deleteFavorite(mem, dto.getProduct_id(), pageable);
    }

    @GetMapping("/mypage_orderlist")
    public List<OrderListTestDto> orderListTest(){
        List<OrderListTestDto> dummyDtoList = new ArrayList<>();

        OrderListTestDto orderListTestDto1 = new OrderListTestDto(
                "2020.07.13(18시 32분)",
                "[코시] 호주산 펫밀크 1L",
                Long.parseLong("1594632706623"),
                6300,
                "배송완료",
                "https://img-cf.kurly.com/shop/data/goods/1562303711815s0.jpg"
        );
        OrderListTestDto orderListTestDto2 = new OrderListTestDto(
                "2021.01.16(20시 05분)",
                "[선물세트] 서울약사신협 석류즙 30포",
                Long.parseLong("3842536821567"),
                15920,
                "배송중",
                "https://img-cf.kurly.com/shop/data/goods/1587357028431s0.jpg"
        );

        dummyDtoList.add(orderListTestDto1);
        dummyDtoList.add(orderListTestDto2);

        return dummyDtoList;
    }

    @GetMapping("/mypage_orderview")
    public OrderDetailDto orderDetailTest(@RequestParam Long ordno){
        OrderDetailDto dummyDto = new OrderDetailDto();
        List<OrderedProductsTestDto> orderedProductsTestDtoList = new ArrayList<>();

        OrderedProductsTestDto orderedProductsTestDto1 = new OrderedProductsTestDto(
                "[코시] 호주산 펫밀크 1L",
                (6300*5),
                5,
                "배송완료"
        );
        OrderedProductsTestDto orderedProductsTestDto2 = new OrderedProductsTestDto(
                "절단 셀러리 500g",
                (2990*10),
                10,
                "배송완료"
        );

        orderedProductsTestDtoList.add(orderedProductsTestDto1);
        orderedProductsTestDtoList.add(orderedProductsTestDto2);

        dummyDto.setOrder_id(ordno);
        dummyDto.setOrderedProductsTestDtoList(orderedProductsTestDtoList);
        dummyDto.setCheckout_total_price(orderedProductsTestDto1.getCheckout_price() + orderedProductsTestDto2.getCheckout_price());
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

    @GetMapping("/mypage_qna")
    public QnaTestDto qnaTest(){
        String[] dummyStr = {
            "배송지연/불만","컬리패스 (무료배송)", "반품문의", "A/S문의", "환불문의", "주문결제문의", "회원정보문의", "취소문의", "교환문의", "상품정보문의", "기타문의"
        };
        Long[] dummyLong = {Long.parseLong("1945327660572"), Long.parseLong("3484593475423")};
        return new QnaTestDto(dummyStr, dummyLong, "noah@fastcampus.com", "010-4321-5678");
    }
}
