package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.order.CheckoutRequestDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class OrdersControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/checkout").contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(
//                        objectMapper.writeValueAsString(
//                                new CheckoutRequestDto(
//                                        "임정우",
//                                        "01066004431",
//                                        "서울시 강동구 암사동 암사아파트",
//                                        "현관 앞",
//                                        "공동 현관 비밀번호(1030#1111#)",
//                                        "오전 7시",
//                                        "신용카드",
//                                        198900,
//                                        1100
//                                ))))
//                .andExpect(status().isOk())
//                .andExpect(content().string("CHECKOUT SUCCESS"));
    }

    @DisplayName("OrderEnd")
    @Test
    void orderEnd() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/order/orderEnd?ordno=15002367").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$.orders_id")).value((long)15002367))
                .andExpect((jsonPath("$.total_price")).value(919018))
                .andExpect((jsonPath("$.orderer")).value("임정우"))
                .andExpect((jsonPath("$.checkout")).value("PAYCO 결제"));
    }
}