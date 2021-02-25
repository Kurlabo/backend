package com.kurlabo.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kurlabo.backend.dto.mypage.DeleteWishListDto;
import com.kurlabo.backend.dto.mypage.InsertWishListDto;
import com.kurlabo.backend.dto.testdto.QnaTestDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.service.DeliverAddressService;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity<?> deleteWishList (@RequestBody @Valid DeleteWishListDto dto, @PageableDefault(size = 5) Pageable pageable) {
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함
        return ResponseEntity.ok(favoriteService.deleteFavorite(mem, dto.getProduct_id(), pageable));
    }

    // 주문 내역 리스트
    @GetMapping("/mypage_orderlist")
    public ResponseEntity<?> orderList(@PageableDefault(size = 3) Pageable pageable) throws JsonProcessingException {
        Member mem = memberService.findById((long)1);
        return ResponseEntity.ok(orderService.getOrderList(mem, pageable));
    }
//    @GetMapping("/mypage_orderview")
//    public ResponseEntity<?> orderDetailTest(@RequestParam Long ordno){
//        OrderDetailDto dummyDto = new OrderDetailDto();
//        List<OrderedProductsTestDto> orderedProductsTestDtoList = new ArrayList<>();
//
//        OrderedProductsTestDto orderedProductsTestDto1 = new OrderedProductsTestDto(
//                "[코시] 호주산 펫밀크 1L",
//                (6300*5),
//                5,
//                "배송완료"
//        );
//        OrderedProductsTestDto orderedProductsTestDto2 = new OrderedProductsTestDto(
//                "절단 셀러리 500g",
//                (2990*10),
//                10,
//                "배송완료"
//        );
//
//        orderedProductsTestDtoList.add(orderedProductsTestDto1);
//        orderedProductsTestDtoList.add(orderedProductsTestDto2);
//
//        dummyDto.setOrder_id(ordno);
//        dummyDto.setOrderedProductsTestDtoList(orderedProductsTestDtoList);
//        dummyDto.setCheckout_total_price(orderedProductsTestDto1.getCheckout_price() + orderedProductsTestDto2.getCheckout_price());
//        dummyDto.setCheckout_method("신용카드");
//        dummyDto.setOrderer_name("박상언");
//        dummyDto.setSender_name("박상언");
//        dummyDto.setCheckout_date("2021-02-06 02:55:00");
//        dummyDto.setReciever_name("임정우");
//        dummyDto.setReciever_phone("010-4321-5678");
//        dummyDto.setReciever_address("(05123) 서울시 성동구 성동로 32 패스트캠퍼스 8층 C강의장");
//        dummyDto.setReciever_recieve_place("문 앞");
//
//        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
//        hh.set("Access-Control-Allow-Origin", "*");
//
//        return ResponseEntity.ok()
//                .headers(hh)
//                .body(dummyDto);
//    }

    // 주문 상세 페이지
    @GetMapping("/mypage_orderview")
    public ResponseEntity<?> orderView(@RequestParam Long ordno) throws JsonProcessingException {
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
