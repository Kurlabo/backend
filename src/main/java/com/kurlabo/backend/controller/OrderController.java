package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.cart.SelectedProductInfoDto;
import com.kurlabo.backend.dto.order.CheckoutRequestDto;
import com.kurlabo.backend.security.jwt.TokenProvider;
import com.kurlabo.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/order")
public class OrderController {

    private final OrderService orderService;
    private final TokenProvider tokenProvider;

    // 주문서
    @GetMapping("/order-sheet")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getOrderSheet(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(orderService.getOrderSheet(tokenProvider.parseTokenToGetMemberId(token)));
    }

    // 장바구니 주문하기 버튼
    @PostMapping("/order-sheet")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> setOrderSheet(@RequestHeader("Authorization") String token, @RequestBody SelectedProductInfoDto dto){
        return ResponseEntity.ok(orderService.setOrdersSheet(tokenProvider.parseTokenToGetMemberId(token), dto));
    }

    // 결제하기
    @PostMapping("/checkout")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> setCheckout(@RequestHeader("Authorization") String token, @RequestBody @Valid CheckoutRequestDto dto) {
        return ResponseEntity.ok(orderService.setCheckout(tokenProvider.parseTokenToGetMemberId(token), dto));
    }

    @GetMapping("/order-end")
    public ResponseEntity<?> orderEnd(@RequestParam Long ordNo){
        return ResponseEntity.ok(orderService.setOrderEnd(ordNo));
    }
}