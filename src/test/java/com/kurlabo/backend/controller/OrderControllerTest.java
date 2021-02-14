package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.converter.StringRevisor;
import com.kurlabo.backend.dto.order.OrderFormRequestDto;
import com.kurlabo.backend.dto.order.OrderFormResponseDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.CartRepository;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.ProductRepository;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class OrderControllerTest {

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

    @DisplayName("MyinfoTest")
    @Test
    void setOrderSheet() throws Exception {
        List<Long> productList = new ArrayList<>(Arrays.asList((long)4, (long)6));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/order/orderSheet").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                new OrderFormRequestDto((long)2, productList))))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.product_cnt[0]").value(1))
//                .andExpect(jsonPath("$.orderer_name").value("양동경"))
//                .andExpect(jsonPath("$.orderer_phone").value("01043215678"))
//                .andExpect(jsonPath("$.orderer_email").value("dkyang@fastcampus.com"))
//                .andExpect(jsonPath("$.orderer_address").value("경기도 고양시 고양동 고양이파트 351번지"));
    }

    @Test
    void test() {


    }
}