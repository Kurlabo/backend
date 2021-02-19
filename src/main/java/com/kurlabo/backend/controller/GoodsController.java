package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.ProductDto;
import com.kurlabo.backend.dto.cart.InsertCartDto;
import com.kurlabo.backend.dto.cart.UpdateCartCntRequestDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.service.CartService;
import com.kurlabo.backend.service.GoodsService;
import com.kurlabo.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/goods")
public class GoodsController {

    private final MemberService memberService;
    private final CartService cartService;
    private final GoodsService goodsService;
//  private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ProductDto goodDetail(@PathVariable(name = "id") Long id) {
        // reviewService.findReviewByProductId(id);

        return goodsService.goodDetail(id);
    }

    // 장바구니 조회
    @GetMapping("/goods_cart")
    public ResponseEntity<?> getCart(@AuthenticationPrincipal Member member){
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(cartService.getCartList(mem));
    }

    // 장바구니 상품 추가
    @PostMapping("/goods_cart")
    public ResponseEntity<?> insertAndUpdateCart(@AuthenticationPrincipal Member member, @RequestBody @Valid InsertCartDto dto){    // Security에서 member 가져와야함
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(cartService.insertCart(mem, dto.getProduct_id(), dto.getCnt()));
    }

    // 장바구니 삭제
    @PostMapping("/goods_cart/delete")
    public ResponseEntity<?> deleteCart(@AuthenticationPrincipal Member member, @RequestBody Long product_id) {
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(cartService.deleteCart(mem, product_id));
    }


    // 장바구니 상품 개수 수정
    @PatchMapping("/goods_cart/{product_id}")
    public ResponseEntity<?> updateCartCnt(@AuthenticationPrincipal Member member
            , @PathVariable Long product_id
            , @RequestBody @Valid UpdateCartCntRequestDto dto){
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(cartService.updateCnt(mem, product_id, dto));
    }
}
