package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.mypage.DeleteWishListDto;
import com.kurlabo.backend.dto.mypage.DeliverAddressDto;
import com.kurlabo.backend.dto.mypage.InsertWishListDto;
import com.kurlabo.backend.dto.mypage.QnaTestDto;
import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.security.jwt.TokenProvider;
import com.kurlabo.backend.service.DeliverAddressService;
import com.kurlabo.backend.service.FavoriteService;
import com.kurlabo.backend.service.OrderService;
import com.kurlabo.backend.service.ReviewService;
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
public class MyPageController {

    private final FavoriteService favoriteService;
    private final ReviewService reviewService;
    private final OrderService orderService;
    private final DeliverAddressService deliverAddressService;
    private final TokenProvider tokenProvider;

    // 늘 사는 것 리스트 불러오기
    @GetMapping("/wish-list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getAllWishList(@RequestHeader("Authorization") String token, @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok(favoriteService.getFavoriteList(tokenProvider.parseTokenToGetMemberId(token), pageable));
    }

    // 늘 사는 것 Insert
    @PostMapping("/wish-list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> insertWishlist(@RequestHeader("Authorization") String token, @RequestBody @Valid InsertWishListDto dto){
        return ResponseEntity.ok(favoriteService.insertFavorite(tokenProvider.parseTokenToGetMemberId(token), dto.getProduct_id()));
    }

    // 늘 사는 것 비우기
    @DeleteMapping("/wish-list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> deleteWishList (@RequestHeader("Authorization") String token, @RequestBody @Valid DeleteWishListDto dto,
                                             @PageableDefault(size = 5) Pageable pageable) {
        return ResponseEntity.ok(favoriteService.deleteFavorite(tokenProvider.parseTokenToGetMemberId(token), dto.getProduct_id(), pageable));
    }

    // 주문 내역 리스트
    @GetMapping("/order-list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> orderList(@RequestHeader("Authorization") String token, @PageableDefault(size = 3) Pageable pageable) {
        return ResponseEntity.ok(orderService.getOrderList(tokenProvider.parseTokenToGetMemberId(token), pageable));
    }

    // 주문 상세 페이지
    @GetMapping("/order-view")
    public ResponseEntity<?> orderView(@RequestParam Long orderNo) {
        return ResponseEntity.ok(orderService.getOrderView(orderNo));
    }

    @GetMapping("/myPageQna")
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

    // 작성가능 후기 리스트
    @GetMapping("/review/view-before-list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> reviewBeforeList(@RequestHeader("Authorization") String token, @PageableDefault(size = 3) Pageable pageable){
        return ResponseEntity.ok().body(reviewService.reviewBeforeList(tokenProvider.parseTokenToGetMemberId(token), pageable));
    }

    // 작성완료 후기 리스트
    @GetMapping("/review/view-after-list")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> reviewAfterList(@RequestHeader("Authorization") String token, @PageableDefault(size = 5) Pageable pageable){
        return ResponseEntity.ok().body(reviewService.reviewAfterList(tokenProvider.parseTokenToGetMemberId(token), pageable));
    }

    // 후기 작성
    @PostMapping("/review")
    @PreAuthorize("authenticated")
    public ResponseEntity<Void> create (@RequestHeader("Authorization") String token, @RequestBody ReviewDto reviewDto) {
        reviewService.createReview(tokenProvider.parseTokenToGetMemberId(token), reviewDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 배송지 리스트
    @GetMapping("/destinations")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getAddressList(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(deliverAddressService.getAllAddress(tokenProvider.parseTokenToGetMemberId(token)));
    }

    // 배송지 추가
    @PostMapping("/destinations")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> createAddress(@RequestHeader("Authorization") String token,
                                           @RequestBody @Valid DeliverAddressDto deliverAddressDto) {
        deliverAddressService.creatAddress(tokenProvider.parseTokenToGetMemberId(token), deliverAddressDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 배송지 수정
    @PutMapping("/destinations")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> updateAddress(@RequestHeader("Authorization") String token,
                                           @RequestBody @Valid DeliverAddressDto deliverAddressDto) {
        deliverAddressService.updateDeliverAddress(tokenProvider.parseTokenToGetMemberId(token), deliverAddressDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // 배송지 삭제
    @DeleteMapping("/destinations")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> deleteAddress(@RequestHeader("Authorization") String token,
                                           @RequestBody @Valid DeliverAddressDto deliverAddressDto) {
        deliverAddressService.deleteDeliverAddress(tokenProvider.parseTokenToGetMemberId(token), deliverAddressDto.getId());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 배송지 체크
    @PatchMapping("/destinations")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> setCheckedAddress(@RequestHeader("Authorization") String token,
                                               @RequestBody @Valid DeliverAddressDto deliverAddressDto) {
        deliverAddressService.setCheckedAddress(tokenProvider.parseTokenToGetMemberId(token), deliverAddressDto.getId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}