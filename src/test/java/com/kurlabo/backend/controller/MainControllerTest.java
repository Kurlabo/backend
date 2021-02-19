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
class MainControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("MainPage")
    @Test
    void mainPage() throws Exception {

    }

//    @DisplayName("MainTest")
//    @Test
//    void MainTestDto() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/main"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.setbyul_img").value("//img-cf.kurly.com/shop/data/main/15/pc_img_1568875999.png"))
//                .andExpect(jsonPath("$.insta_thumbnail_img_url[0]").value("https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145829497_429910031618977_2686700364585205332_n.jpg?_nc_cat=101&ccb=2&_nc_sid=8ae9d6&_nc_ohc=-WkuqU3BZCYAX9V-8cf&_nc_oc=AQma5Jem99_iE8KN-5ZeuW8WTFP9Ffa0d88we6liTpfSpVtG-ZvhHI2xOFxZeupMS-Q&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=70036b1b024889031d3db2652ad738af&oe=6042CF55"))
//                .andExpect(jsonPath("$.insta_landing_url[0]").value("https://www.instagram.com/p/CK5HdanJLXa/"))
//                .andExpect(jsonPath("$.slide_img[0]").value("https://img-cf.kurly.com/shop/data/main/1/pc_img_1612507314.jpg"))
//                .andExpect(jsonPath("$.product_id").value((long)2))
//                .andExpect(jsonPath("$.short_description").value("껍질째 먹을 수 있는 친환경 흙당근 (500g 내외)"))
//                .andExpect(jsonPath("$.name").value("친환경 당근 500g"))
//                .andExpect(jsonPath("$.original_price").value(3300))
//                .andExpect(jsonPath("$.discounted_price").value(3135))
//                .andExpect(jsonPath("$.original_image_url").value("https://img-cf.kurly.com/shop/data/goods/1609141186826l0.jpg"))
//                .andExpect(jsonPath("$.sticker_image_url").value("https://img-cf.kurly.com/shop/data/my_icon/icon_farming_coupon_20_percent.png"))
//                .andExpect(jsonPath("$.category").value(0));
//
//    }
}