package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.cart.DeleteCartRequestDto;
import com.kurlabo.backend.dto.cart.InsertCartRequestDto;
import com.kurlabo.backend.dto.cart.UpdateCartCntRequestDto;
import com.kurlabo.backend.security.jwt.TokenProvider;
import com.kurlabo.backend.service.CartService;
import com.kurlabo.backend.service.GoodsService;
import com.kurlabo.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/goods")
public class GoodsController {
    private final CartService cartService;
    private final GoodsService goodsService;
    private final ReviewService reviewService;
    private final TokenProvider tokenProvider;

    @GetMapping("/{productId}")
    public ResponseEntity<?> getGoods(@PathVariable Long productId) {
        return ResponseEntity.ok(goodsService.getGoods(productId));
    }

    @GetMapping("/{productId}/goods-detail")
    public ResponseEntity<?> getGoodsDetail(@PathVariable Long productId) {
        return ResponseEntity.ok(goodsService.getGoodsDetail(productId));
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<?> getGoodsReviews(@PageableDefault(size = 7) Pageable pageable,
                                                     @PathVariable Long productId) {
        return ResponseEntity.ok(goodsService.getGoodsReview(productId, pageable));
    }

    @GetMapping("/increase-review-cnt/{reviewId}")
    public ResponseEntity<?> increaseReviewCnt(@PathVariable Long reviewId){
        return ResponseEntity.ok(reviewService.increaseReviewCnt(reviewId));
    }

    @GetMapping("/increase-review-help/{reviewId}")
    public ResponseEntity<?> increaseReviewHelp(@PathVariable Long reviewId){
        return ResponseEntity.ok(reviewService.increaseReviewHelp(reviewId));
    }

    // 장바구니 조회
    @GetMapping("/goods-cart")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(cartService.getCartList(tokenProvider.parseTokenToGetMemberId(token)));
    }

    // 장바구니 상품 추가
    @PostMapping("/goods-cart")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> insertAndUpdateCart(@RequestHeader("Authorization") String token, @RequestBody @Valid InsertCartRequestDto requestDto){
        return ResponseEntity.ok(cartService.insertCart(tokenProvider.parseTokenToGetMemberId(token), requestDto));
    }

    // 장바구니 삭제
    @DeleteMapping("/goods-cart")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> deleteCart(@RequestHeader("Authorization") String token, @RequestBody DeleteCartRequestDto requestDto) {
        return ResponseEntity.ok(cartService.deleteCart(tokenProvider.parseTokenToGetMemberId(token), requestDto.getProduct_id()));
    }

    // 장바구니 상품 개수 수정
    @PatchMapping("/goods-cart")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> updateCartCnt(@RequestHeader("Authorization") String token, @RequestBody @Valid UpdateCartCntRequestDto requestDto){
        return ResponseEntity.ok(cartService.updateCnt(tokenProvider.parseTokenToGetMemberId(token), requestDto));
    }

    // 상품 리스트
    @GetMapping("/goods-list")
    public ResponseEntity<?> goodsList(@RequestParam int category, @PageableDefault(size = 6) Pageable pageable){
        return ResponseEntity.ok(goodsService.getGoodsList(category, pageable));
    }
}