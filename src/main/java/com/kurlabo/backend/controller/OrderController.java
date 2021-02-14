package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.order.OrderFormRequestDto;
import com.kurlabo.backend.dto.order.OrderFormResponseDto;
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
    public OrderFormResponseDto setOrderSheet(@RequestBody @Valid OrderFormRequestDto dto){

        System.out.println("orderFormResponseDto >>>>>>>>>>>>>>>>>>>>>>>>>> " + orderService.setOrderSheet(dto));

        return orderService.setOrderSheet(dto);
    }

    // 결제하기
    @PostMapping("/checkout")
    public String setCheckout(){

        return "Checkout Success!";
    }
}
