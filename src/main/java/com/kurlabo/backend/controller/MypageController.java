package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.mypage.DeleteWishListDto;
import com.kurlabo.backend.dto.mypage.DeliverAddressDto;
import com.kurlabo.backend.dto.mypage.InsertWishListDto;
import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.dto.testdto.QnaTestDto;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.security.jwt.TokenProvider;
import com.kurlabo.backend.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/mypage")
public class MypageController {

    private final FavoriteService favoriteService;
    private final ReviewService reviewService;
    private final OrderService orderService;
    private final DeliverAddressService deliverAddressService;
    private final TokenProvider tokenProvider;

    //@AuthenticationPrincipal Member member,
    // 늘 사는 것 리스트 불러오기
    @GetMapping("/mypage_wishlist")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getAllWishList(@RequestHeader("Authorization") String token, @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(favoriteService.getFavoriteList(token, pageable));
    }

    // 늘 사는 것 Insert
    @PostMapping("/mypage_wishlist")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> insertWishlist(@RequestHeader("Authorization") String token, @RequestBody @Valid InsertWishListDto dto){
        return ResponseEntity.ok(favoriteService.insertFavorite(token, dto.getProduct_id()));
    }

    // @AuthenticationPrincipal Member member,
    // 늘 사는 것 비우기
    @DeleteMapping("/mypage_wishlist")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> deleteWishList (@RequestHeader("Authorization") String token, @RequestBody @Valid DeleteWishListDto dto, @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(favoriteService.deleteFavorite(token, dto.getProduct_id(), pageable));
    }

    // 주문 내역 리스트
    @GetMapping("/mypage_orderlist")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> orderList(@RequestHeader("Authorization") String token, @PageableDefault(size = 3) Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrderList(token, pageable));
    }

    // 주문 상세 페이지
    @GetMapping("/mypage_orderview")
    public ResponseEntity<?> orderView(@RequestParam Long ordno) {
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

    @GetMapping("/writable-reviews")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> writableReviews(@RequestHeader("Authorization") String token){
        // 작성가능 후기 리스트
        return ResponseEntity.ok()
                .body(reviewService.reviewList(token, 0));
    }

    @GetMapping("/written-reviews")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> writtenReviews(@RequestHeader("Authorization") String token){
        // 작성완료 후기 리스트
        return ResponseEntity.ok()
                .body(reviewService.reviewList(token, 1));
    }

    @PostMapping("/mypage_review/{pId}")
    @PreAuthorize("authenticated")
    public ResponseEntity<Void> create (@RequestHeader("Authorization") String token, @PathVariable Long pId, @RequestBody ReviewDto reviewDto) {
        // 후기 작성
        reviewService.create(token, pId, reviewDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 배송지 리스트
    @GetMapping("/destination/list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getAllAddress(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(deliverAddressService.getAllAddress(tokenProvider.parseTokenToGetMemberId(token)));
    }

    // 배송지 추가
    @PostMapping("/destination/list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> createAddress(@RequestHeader("Authorization") String token,
                                           @RequestBody @Valid DeliverAddressDto deliverAddress) {
        deliverAddressService.creatAddress(tokenProvider.parseTokenToGetMemberId(token), deliverAddress);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 배송지 수정
    @PutMapping("/destination/list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> updateAddress(@RequestHeader("Authorization") String token,
                                           @RequestBody @Valid Deliver_Address deliverAddress) {
        deliverAddressService.updateDeliverAddress(tokenProvider.parseTokenToGetMemberId(token), deliverAddress);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 배송지 삭제
    @DeleteMapping("/destination/list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> deleteAddress(@RequestHeader("Authorization") String token,
                                           @RequestBody @Valid Deliver_Address deliverAddress) {
        deliverAddressService.deleteDeliverAddress(tokenProvider.parseTokenToGetMemberId(token), deliverAddress);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        // return ResponseEntity.ok(deliverAddressService.deleteDeliverAddress(deliverAddress));
    }

    @PatchMapping("/destination/list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> updateChkAddress(@RequestHeader("Authorization") String token,
                                           @RequestBody @Valid Deliver_Address deliverAddress) {
        deliverAddressService.updateChkAddress(tokenProvider.parseTokenToGetMemberId(token), deliverAddress);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}