package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CartControllerTest {

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

    @DisplayName("CartTest")
    @Test
    void cartTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/shop/goods/goods_cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productInfoDto[0].product_id").value((long)1))
                .andExpect(jsonPath("$.productInfoDto[0].category").value(0))
                .andExpect(jsonPath("$.productInfoDto[0].name").value("한끼 당근 1개"))
                .andExpect(jsonPath("$.productInfoDto[0].short_description").value("딱 하나만 필요할 때 한끼 당근"))
                .andExpect(jsonPath("$.productInfoDto[0].original_price").value(1300))
                .andExpect(jsonPath("$.productInfoDto[0].discounted_price").value(1300))
                .andExpect(jsonPath("$.productInfoDto[0].original_image_url").value("https://img-cf.kurly.com/shop/data/goods/1583285919646l0.jpg"))
                .andExpect(jsonPath("$.productInfoDto[0].sticker_image_url").value("https://img-cf.kurly.com/shop/data/my_icon/icon_farming_coupon_20_percent.png"))
                .andExpect(jsonPath("$.productInfoDto[0].packing_type_text").value("냉장/종이포장"))
                .andExpect(jsonPath("$.userInfoDto.member_id").value((long)1))
                .andExpect(jsonPath("$.userInfoDto.uid").value("noah"))
                .andExpect(jsonPath("$.userInfoDto.address").value("서울시 성동구 아차산로 18 (뚝섬역)"));
    }


}