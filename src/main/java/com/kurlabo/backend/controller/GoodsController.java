package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.cart.*;
import com.kurlabo.backend.dto.goods.ProductDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.service.CartService;
import com.kurlabo.backend.service.GoodsService;
import com.kurlabo.backend.service.MemberService;
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

    private final MemberService memberService;
    private final CartService cartService;
    private final GoodsService goodsService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> goodDetail(@PageableDefault(size = 7) Pageable pageable,
                                                 @PathVariable(name = "id") Long id) {

        return ResponseEntity.ok(goodsService.goodDetail(pageable, id));
    }

    @PostMapping("/{pid}/{rid}")
    public void reviewHelpCount(@PathVariable(name = "rid") Long rid, @PathVariable(name = "pid") Long pid, Review review) {
        goodsService.reviewHelpCount(review);
    }


    //@AuthenticationPrincipal Member member
    // 장바구니 조회
    @GetMapping("/goods_cart")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getCart(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(cartService.getCartList(token));
    }

    //@AuthenticationPrincipal Member member,
    // 장바구니 상품 추가
    @PostMapping("/goods_cart")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> insertAndUpdateCart(@RequestHeader("Authorization") String token, @RequestBody @Valid InsertCartRequestDto dto){
        return ResponseEntity.ok(cartService.insertCart(token, dto));
    }

    // @AuthenticationPrincipal Member member,
    // 장바구니 삭제
    @PostMapping("/goods_cart/delete")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> deleteCart(@RequestHeader("Authorization") String token, @RequestBody DeleteCartRequestDto dto) {
        return ResponseEntity.ok(cartService.deleteCart(token, dto.getProduct_id()));
    }

    // @AuthenticationPrincipal Member member,
    // 장바구니 상품 개수 수정
    @PatchMapping("/goods_cart/{product_id}")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> updateCartCnt(@RequestHeader("Authorization") String token, @PathVariable Long product_id
            , @RequestBody @Valid UpdateCartCntRequestDto dto){
        return ResponseEntity.ok(cartService.updateCnt(token, product_id, dto));
    }

    // 장바구니 주문하기 버튼
    @PostMapping("/goods_cart/orderSheet")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> setOrderSheet(@RequestHeader("Authorization") String token, @RequestBody SelectedProductInfoDto dto){
        return ResponseEntity.ok(cartService.setOrdersSheet(token, dto));
    }

    // 상품 리스트
    @GetMapping("/goods_list")
    public ResponseEntity<?> goodsList(@RequestParam int category, @PageableDefault(size = 6) Pageable pageable){
        return ResponseEntity.ok(goodsService.getGoodsList(category, pageable));
    }
}