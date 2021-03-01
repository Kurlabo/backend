package com.kurlabo.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kurlabo.backend.dto.order.CheckoutRequestDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.service.MemberService;
import com.kurlabo.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/order")
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;

    // 주문서
    @GetMapping("/orderSheet")
    public ResponseEntity<?> getOrderSheet(){
        Member mem = memberService.findById((long)1);       // 나중에 Spring Security 완성되면 Principal에서 member_id 가져와야함, 로그인 하지 않았을 때 Exception 발생시켜야함
        return ResponseEntity.ok(orderService.getOrderSheet(mem));
    }

    // 결제하기
    @PostMapping("/checkout")
    public ResponseEntity<?> setCheckout(@RequestBody @Valid CheckoutRequestDto dto) throws JsonProcessingException {
        String returnStr = "결제에 실패하셨습니다.";
        returnStr = orderService.setCheckout(dto);
        return ResponseEntity.ok(returnStr);
    }
}