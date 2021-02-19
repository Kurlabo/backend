package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.order.CheckoutRequestDto;
import com.kurlabo.backend.dto.order.OrderSheetRequestDto;
import com.kurlabo.backend.dto.order.OrderSheetResponseDto;
import com.kurlabo.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/order")
public class OrderController {

    private final OrderService orderService;

    // 주문서
    @PostMapping("/orderSheet")
    public ResponseEntity<?> setOrderSheet(@RequestBody @Valid OrderSheetRequestDto dto){

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(orderService.getOrderSheet(dto));
    }

    // 결제하기
    @PostMapping("/checkout")
    public ResponseEntity<?> setCheckout(@RequestBody @Valid CheckoutRequestDto dto){
        String returnStr = "결제에 실패하셨습니다.";

        returnStr = orderService.setCheckout(dto);

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(returnStr);
    }
}
