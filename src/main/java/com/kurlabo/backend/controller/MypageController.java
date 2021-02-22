package com.kurlabo.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kurlabo.backend.dto.mypage.DeleteWishListDto;
import com.kurlabo.backend.dto.mypage.InsertWishListDto;
import com.kurlabo.backend.dto.testdto.*;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.service.FavoriteService;
import com.kurlabo.backend.service.MemberService;
import com.kurlabo.backend.service.OrderService;
import com.kurlabo.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/mypage")
public class MypageController {

    private final FavoriteService favoriteService;
    private final MemberService memberService;
    private final ReviewService reviewService;
    private final OrderService orderService;

    //@AuthenticationPrincipal Member member,
    // 늘 사는 것 리스트 불러오기
    @GetMapping("/mypage_wishlist")
    public ResponseEntity<?> getAllWishList(@PageableDefault(size = 5) Pageable pageable){
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함
        return ResponseEntity.ok(favoriteService.getFavoriteList(mem, pageable));
    }

    // 늘 사는 것 Insert
    @PostMapping("/mypage_wishlist")
    public ResponseEntity<?> insertWishlist(@RequestBody @Valid InsertWishListDto dto){
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함
        return ResponseEntity.ok(favoriteService.insertFavorite(mem, dto.getProduct_id()));
    }

    // @AuthenticationPrincipal Member member,
    // 늘 사는 것 비우기
    @DeleteMapping("/mypage_wishlist")
    public ResponseEntity<?> deleteWishList (@RequestBody @NotNull DeleteWishListDto dto, @PageableDefault(size = 5) Pageable pageable) {
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함
        return ResponseEntity.ok(favoriteService.deleteFavorite(mem, dto.getProduct_id(), pageable));
    }



    @GetMapping("/mypage_orderlist")
    public ResponseEntity<?> orderList(){
        Member mem = memberService.findById((long)1);

        return ResponseEntity.ok(null);
    }

    @GetMapping("/mypage_orderview")
    public ResponseEntity<?> orderView(@RequestParam(required = true) Long ordno) throws JsonProcessingException {
        return ResponseEntity.ok(orderService.getOrderView(ordno));
    }

    @GetMapping("/mypage_qna")
    public ResponseEntity<?> qnaTest(){
        String[] dummyStr = {
            "배송지연/불만","컬리패스 (무료배송)", "반품문의", "A/S문의", "환불문의", "주문결제문의", "회원정보문의", "취소문의", "교환문의", "상품정보문의", "기타문의"
        };
        Long[] dummyLong = {Long.parseLong("1945327660572"), Long.parseLong("3484593475423")};

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(new QnaTestDto(dummyStr, dummyLong, "noah@fastcampus.com", "010-4321-5678"));
    }

//    @GetMapping("/written-reviews")
//    public ResponseEntity<?> writtenReviews(@PageableDefault Pageable pageable, Review review){
//        // 작성완료 후기 리스트
//        return ResponseEntity.ok()
//                .body(reviewService.reviewList(pageable, review));
//    }
//
//    @GetMapping("/writable-reviews")
//    public ResponseEntity<?> writableReviews(@PageableDefault Pageable pageable, Review review){
//        // 작성가능 후기 리스트
//        return ResponseEntity.ok()
//                .body(reviewService.reviewList(pageable, review));
//    }

    //mypage_review.php?write_goodsno=53329
    @PostMapping("/mypage_review/{pId}")
    public ResponseEntity<Void> create (@PathVariable Long pId, Review review) {
        // 후기 작성
        reviewService.conditionsChk(review);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
