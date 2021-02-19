package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.cart.InsertCartDto;
import com.kurlabo.backend.dto.cart.UpdateCartCntRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class GoodsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void before(WebApplicationContext was) {
        mockMvc = MockMvcBuilders.webAppContextSetup(was)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void getProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/1"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @DisplayName("GetCartList")
    @Test
    void getCartList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/goods_cart"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.cartDataDto[4].product_id").value((long)69))
                .andExpect(jsonPath("$.cartDataDto[4].name").value("[바다원] 올리브유 김자반 볶음 50g"))
                .andExpect(jsonPath("$.cartDataDto[4].original_price").value(1990))
                .andExpect(jsonPath("$.cartDataDto[4].discounted_price").value(1990))
                .andExpect(jsonPath("$.cartDataDto[4].packing_type_text").value("냉장/종이포장"))
                .andExpect(jsonPath("$.cartDataDto[4].min_ea").value(1))
                .andExpect(jsonPath("$.cartDataDto[4].max_ea").value(99))
                .andExpect(jsonPath("$.cartDataDto[4].list_image_url").value("https://img-cf.kurly.com/shop/data/goods/1589264017102s0.jpg"))
                .andExpect(jsonPath("$.cartDataDto[4].cnt").value(6))
                .andExpect(jsonPath("$.address").value("서울시 강동구 고덕동 삼성아파트 111동 111호"));
    }

    @DisplayName("InsertCart")
    @Test
    void insertAndUpdateCart() throws Exception {
        String content = objectMapper.writeValueAsString(new InsertCartDto((long)109, 3));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/goods_cart")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$").value("Increase cart cnt succeed"));
    }

    @DisplayName("DeleteCart")
    @Test
    void deleteCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/goods_cart/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString((long)99)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("UpdateCartCnt")
    @Test
    void updateCartCnt() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/goods/goods_cart/99")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(
                        new UpdateCartCntRequestDto(1)
                )))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
