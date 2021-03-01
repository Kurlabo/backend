package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.order.CheckoutRequestDto;
import com.kurlabo.backend.dto.order.OrderListDto;
import com.kurlabo.backend.dto.order.OrderSheetRequestDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class OrdersControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("OrderSheet")
    @Test
    void getOrderSheet() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/orderSheet")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @DisplayName("Checkout")
    @Test
    void setCheckout() throws Exception {
//        LocalDate localDate = LocalDate.of(2021,02,15);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/checkout").contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(
//                        objectMapper.writeValueAsString(
//                                new CheckoutRequestDto(
//                                        (long)1,
//                                        "임정우",
//                                        "01066004431",
//                                        "서울시 강동구 암사동 암사아파트",
//                                        "현관 앞",
//                                        "공동 현관 비밀번호(1030#1111#)",
//                                        "오전 7시",
//                                        localDate,
//                                        "신용카드",
//                                        new ArrayList<>(Arrays.asList(new OrderListDto((long)1, 5), new OrderListDto((long)8,3))),
//                                        198900
//                                ))))
//                .andExpect(status().isOk())
//                .andExpect(content().string("결제에 성공하셨습니다."));
    }
}