package com.kurlabo.backend.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
public class GoodsControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void getProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.product_id").value(1L))
                .andExpect(jsonPath("$.name").value("[KF365] 감자 1kg"))
                .andExpect(jsonPath("$.short_description").value("믿고 먹을 수 있는 상품을 합리적인 가격에, KF365"))
                .andExpect(jsonPath("$.original_price").value(2500))
                .andExpect(jsonPath("$.discounted_price").value(2500))
                .andExpect(jsonPath("$.detail_image_url").value("https://img-cf.kurly.com/shop/data/goods/153017237655m0.jpg"))
                .andExpect(jsonPath("$.original_image_url").value("https://img-cf.kurly.com/shop/data/goods/1530172373295l0.jpg"))
                .andExpect(jsonPath("$.productDetailDto.productDetailId").value(1L))
                .andExpect(jsonPath("$.productDetailDto.detailImgUrl").value("//img-cf.kurly.com/shop/data/goodsview/20180628/gv40000026292_1.jpg"))
                .andExpect(jsonPath("$.productDetailDto.detailTitle").value("\\n포슬포슬하고 고소한 맛\\n감자 1kg\\n"))
                .andExpect(jsonPath("$.productDetailDto.detailContext").value("간단히 쪄 먹기도 좋고, 다양한 요리와 함께 곁들여 먹기도 좋은 감자는 우리 식탁에 빼놓을 수 없는 식재료지요. 탄수화물은 물론이고 단백질, 비타민C까지 풍부해 마치 곡류와 채소를 동시에 먹은 것과 같은 효과를 줍니다. 컬리는 그때그때 유명산지 감자를 가락시장에서 수급하여 보내드립니다. 포슬포슬한 식감에 고소하고 은은한 단맛이 나 볶음, 구이, 튀김 등 다양하게 요리해서 먹을 수 있어요. 매일 식탁에 올려도 질리지 않는 감자를 컬리에서 간편하게 만나보세요."))
                .andExpect(jsonPath("$.productDetailDto.productImgUrl").value("//img-cf.kurly.com/shop/data/goodsview/20210202/gv10000156055_1.jpg"));
    }

    @DisplayName("CartTest")
    @Test
    void cartTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/goods_cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_info_dto[0].product_id").value((long)1))
                .andExpect(jsonPath("$.product_info_dto[0].category").value(0))
                .andExpect(jsonPath("$.product_info_dto[0].name").value("한끼 당근 1개"))
                .andExpect(jsonPath("$.product_info_dto[0].short_description").value("딱 하나만 필요할 때 한끼 당근"))
                .andExpect(jsonPath("$.product_info_dto[0].original_price").value(1300))
                .andExpect(jsonPath("$.product_info_dto[0].discounted_price").value(1300))
                .andExpect(jsonPath("$.product_info_dto[0].original_image_url").value("https://img-cf.kurly.com/shop/data/goods/1583285919646l0.jpg"))
                .andExpect(jsonPath("$.product_info_dto[0].sticker_image_url").value("https://img-cf.kurly.com/shop/data/my_icon/icon_farming_coupon_20_percent.png"))
                .andExpect(jsonPath("$.product_info_dto[0].packing_type_text").value("냉장/종이포장"))
                .andExpect(jsonPath("$.user_info_dto.member_id").value((long)1))
                .andExpect(jsonPath("$.user_info_dto.uid").value("noah"))
                .andExpect(jsonPath("$.user_info_dto.address").value("서울시 성동구 아차산로 18 (뚝섬역)"));
    }


}
