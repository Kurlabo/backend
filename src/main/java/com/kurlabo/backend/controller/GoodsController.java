package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.cart.DeleteCartRequestDto;
import com.kurlabo.backend.dto.cart.InsertCartRequestDto;
import com.kurlabo.backend.dto.cart.UpdateCartCntRequestDto;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.security.jwt.TokenProvider;
import com.kurlabo.backend.service.CartService;
import com.kurlabo.backend.service.GoodsService;
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
    private final TokenProvider tokenProvider;

    @GetMapping("/{id}")
    public ResponseEntity<?> getGoods(@PathVariable(name = "id") Long productId) {
        return ResponseEntity.ok(goodsService.getGoods(productId));
    }

    @GetMapping("/{id}/goods_detail")
    public ResponseEntity<?> getGoodsDetail(@PathVariable(name = "id") Long productId) {
        return ResponseEntity.ok(goodsService.getGoodsDetail(productId));
    }

    @GetMapping("/{id}/reviews")
    public ResponseEntity<?> getGoodsReviews(@PageableDefault(size = 7) Pageable pageable,
                                                     @PathVariable(name = "id") Long productId) {
        return ResponseEntity.ok(goodsService.getGoodsReview(pageable, productId));
    }

    // 리팩토링 필요
    @PostMapping("/{pid}/{rid}")
    public void reviewHelpCount(@PathVariable(name = "rid") Long rid, @PathVariable(name = "pid") Long pid, Review review) {
        goodsService.reviewHelpCount(review);
    }

    // 장바구니 조회
    @GetMapping("/goods_cart")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(cartService.getCartList(tokenProvider.parseTokenToGetMemberId(token)));
    }

    // 장바구니 상품 추가
    @PostMapping("/goods_cart")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> insertAndUpdateCart(@RequestHeader("Authorization") String token, @RequestBody @Valid InsertCartRequestDto dto){
        return ResponseEntity.ok(cartService.insertCart(tokenProvider.parseTokenToGetMemberId(token), dto));
    }

    // 장바구니 삭제
    @PostMapping("/goods_cart/delete")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> deleteCart(@RequestHeader("Authorization") String token, @RequestBody DeleteCartRequestDto dto) {
        return ResponseEntity.ok(cartService.deleteCart(tokenProvider.parseTokenToGetMemberId(token), dto.getProduct_id()));
    }

    // 장바구니 상품 개수 수정
    @PatchMapping("/goods_cart/{product_id}")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> updateCartCnt(@RequestHeader("Authorization") String token, @PathVariable Long product_id
            , @RequestBody @Valid UpdateCartCntRequestDto dto){
        return ResponseEntity.ok(cartService.updateCnt(tokenProvider.parseTokenToGetMemberId(token), product_id, dto));
    }

    // 상품 리스트
    @GetMapping("/goods_list")
    public ResponseEntity<?> goodsList(@RequestParam int category, @PageableDefault(size = 6) Pageable pageable){
        return ResponseEntity.ok(goodsService.getGoodsList(category, pageable));
    }
}