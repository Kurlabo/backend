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
    void setOrderSheet() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/orderSheet").contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(new OrderSheetRequestDto((long)2))))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.orderer_name").value("임정우"))
//                .andExpect(jsonPath("$.orderer_phone").value("01043215678"))
//                .andExpect(jsonPath("$.orderer_email").value("noah@fastcampus.com"))
//                .andExpect(jsonPath("$.orderer_address").value("서울시 강동구 고덕동 삼성아파트 111동 111호"));
    }

    @DisplayName("Checkout")
    @Test
    void setCheckout() throws Exception {
        LocalDate localDate = LocalDate.of(2021,01,11);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/checkout").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                new CheckoutRequestDto(
                                        (long)1,
                                        "조재연",
                                        "01092928383",
                                        "서울시 우리구 우리동 우리아파트",
                                        "집 앞",
                                        "기타장소 계단 밑",
                                        "오전 7시",
                                        localDate,
                                        "신용카드",
                                        new ArrayList<>(Arrays.asList(new OrderListDto((long)139, 3), new OrderListDto((long)211,3))),
                                        23100
                                ))))
                .andExpect(status().isOk())
                .andExpect(content().string("결제에 성공하셨습니다."));
    }
}