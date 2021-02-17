package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.order.CheckoutRequestDto;
import com.kurlabo.backend.dto.order.OrderSheetRequestDto;
import com.kurlabo.backend.dto.order.OrderSheetResponseDto;
import com.kurlabo.backend.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/order")
public class OrderController {

    private final OrderService orderService;

    // 주문서
    @PostMapping("/orderSheet")
    public OrderSheetResponseDto setOrderSheet(@RequestBody @Valid OrderSheetRequestDto dto){
        return orderService.getOrderSheet(dto);
    }

    // 결제하기
    @PostMapping("/checkout")
    public String setCheckout(@RequestBody @Valid CheckoutRequestDto dto){
        String returnStr = "결제에 실패하셨습니다.";

        returnStr = orderService.setCheckout(dto);

        return returnStr;
    }
}
