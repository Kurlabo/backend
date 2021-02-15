package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.order.CheckoutRequestDto;
import com.kurlabo.backend.dto.order.OrderSheetRequestDto;
<<<<<<< HEAD
=======
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
>>>>>>> release/v0.0.1210215001
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
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/orderSheet").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                new OrderSheetRequestDto((long)2, new ArrayList<>(Arrays.asList((long)4, (long)6))))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_cnt[0]").value(1))
                .andExpect(jsonPath("$.orderer_name").value("양동경"))
                .andExpect(jsonPath("$.orderer_phone").value("01043215678"))
                .andExpect(jsonPath("$.orderer_email").value("dkyang@fastcampus.com"))
                .andExpect(jsonPath("$.orderer_address").value("경기도 고양시 고양동 고양이파트 351번지"));
    }

    @DisplayName("Checkout")
    @Test
    void setCheckout() throws Exception {
        LocalDate localDate = LocalDate.now();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/checkout").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                new CheckoutRequestDto(
                                        (long)2,
                                        "임정우",
                                        "01087239582",
                                        "서울시 강남구 강남동 강낭콩",
                                        "집 앞",
                                        "공동 현관 비밀번호(1030#1111#)",
                                        "배송 직후",
                                        localDate,
                                        "신용카드",
                                        new ArrayList<>(Arrays.asList((long)34, (long)109, (long)31)),
                                        36900
                                ))))
                .andExpect(status().isOk())
                .andExpect(content().string("결제에 성공하셨습니다."));
    }

    @Test
    void test() {
<<<<<<< HEAD

=======
        Member member = memberRepository.findById((long)2).orElseThrow(()->new ResourceNotFoundException());
        System.out.println(member);
>>>>>>> release/v0.0.1210215001
    }
}
