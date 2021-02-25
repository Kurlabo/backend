package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.cart.DeleteCartRequestDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/172"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.product_id").value((long)172));
    }

//    @DisplayName("상품후기 리뷰 리스트")
//    @Test
//    void getReview() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/1/1"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.review_id").value((long)1));
//    }

    @DisplayName("상품후기 도움이 돼요")
    @Test
    void reviewHelpCnt() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/1/1")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString((long)1)))
//                .andExpect(status().isOk())
//                .andDo(print());

    }

    @DisplayName("GetCartList")
    @Test
    void getCartList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/goods_cart"))
                .andExpect(status().isOk())
                .andDo(print());
//                .andExpect(jsonPath("$.cartDataDto[4].product_id").value((long)69))
//                .andExpect(jsonPath("$.cartDataDto[4].name").value("[바다원] 올리브유 김자반 볶음 50g"))
//                .andExpect(jsonPath("$.cartDataDto[4].original_price").value(1990))
//                .andExpect(jsonPath("$.cartDataDto[4].discounted_price").value(1990))
//                .andExpect(jsonPath("$.cartDataDto[4].packing_type_text").value("냉장/종이포장"))
//                .andExpect(jsonPath("$.cartDataDto[4].min_ea").value(1))
//                .andExpect(jsonPath("$.cartDataDto[4].max_ea").value(99))
//                .andExpect(jsonPath("$.cartDataDto[4].list_image_url").value("https://img-cf.kurly.com/shop/data/goods/1589264017102s0.jpg"))
//                .andExpect(jsonPath("$.cartDataDto[4].cnt").value(6))
//                .andExpect(jsonPath("$.address").value("서울시 강동구 고덕동 삼성아파트 111동 111호"));
    }

    @DisplayName("InsertCart")
    @Test
    void insertAndUpdateCart() throws Exception {
//        List<InsertCartDto> lists = new ArrayList<>(Arrays.asList(
////                new InsertCartDto((long)101, 3),
////                new InsertCartDto((long)250, 1),
//                new InsertCartDto((long)201, 1)
//        ));
//        String content = objectMapper.writeValueAsString(new InsertCartRequestDto(lists));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/goods_cart")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(content))
//                .andExpect(status().isOk());
////                .andExpect(jsonPath("$").value("Increase cart cnt succeed"));
    }

    @DisplayName("DeleteCart")
    @Test
    void deleteCart() throws Exception {
        List<Long> lists = new ArrayList<>(Arrays.asList((long)52,(long)109));
//        List<Long> lists = new ArrayList<>();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/goods_cart/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(new DeleteCartRequestDto(lists))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("UpdateCartCnt")
    @Test
    void updateCartCnt() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/goods/goods_cart/25")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(
//                        new UpdateCartCntRequestDto(1)
//                )))
//                .andExpect(status().isOk());
    }

    @DisplayName("GoodsList")
    @Test
    void goodsList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/goods_list?category=200")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
//                .param("page", String.valueOf(1)))
                .andExpect(status().isOk());
//                .andExpect((jsonPath("$[3].product_id").value((long)12)))     // 자식 카테고리 Test
//                .andExpect((jsonPath("$[3].original_image_url").value("https://img-cf.kurly.com/shop/data/goods/1490946589409l0.jpg")))
//                .andExpect((jsonPath("$[3].sticker_image_url").value("https://img-cf.kurly.com/shop/data/my_icon/icon_farming_coupon_20_percent.png")))
//                .andExpect((jsonPath("$[3].name").value("무농약 깐 양파 1개")))
//                .andExpect((jsonPath("$[3].original_price").value(1250)))
//                .andExpect((jsonPath("$[3].discounted_price").value(1250)))
//                .andExpect((jsonPath("$[3].discount_percent").value(0)))
//                .andExpect((jsonPath("$[3].short_description").value("신선하고 깔끔하게 쓰는 양파(1개/100g내외)")));
    }
}