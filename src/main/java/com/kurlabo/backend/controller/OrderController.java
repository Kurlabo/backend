package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.order.CheckoutRequestDto;
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

    // 주문서
    @GetMapping("/orderSheet")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getOrderSheet(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(orderService.getOrderSheet(token));
    }

    // 결제하기
    @PostMapping("/checkout")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> setCheckout(@RequestHeader("Authorization") String token, @RequestBody @Valid CheckoutRequestDto dto) {
        String returnStr = "결제에 실패하셨습니다.";
        returnStr = orderService.setCheckout(token, dto);
        return ResponseEntity.ok(returnStr);
    }

    @GetMapping("/orderEnd")
    public ResponseEntity<?> orderEnd(@RequestParam Long ordno){
        return ResponseEntity.ok(orderService.setOrderEnd(ordno));
    }
}