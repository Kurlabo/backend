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
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/main"))
                .andExpect(status().isOk())
                .andDo(print());
//                .andExpect(jsonPath("$.slide_img_list[1]").value("https://img-cf.kurly.com/shop/data/main/1/pc_img_1612094297.jpg"))
//                .andExpect(jsonPath("$.instaSrcDto.landing_url_list[2]").value("https://www.instagram.com/p/CK0Fk8Opzlc/"))
//                .andExpect(jsonPath("$.instaSrcDto.thumbnail_img_list[3]").value("https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145166627_690796704929009_3137692220607683881_n.jpg?_nc_cat=102&ccb=2&_nc_sid=8ae9d6&_nc_ohc=UvQUg7XRIHoAX_HhY9K&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=0ccd9a2217dd955f4076fe0ed1e0822e&oe=60494680"))
//                .andExpect(jsonPath("$.setbyul_img").value("//img-cf.kurly.com/shop/data/main/15/pc_img_1568875999.png"));
    }

    @DisplayName("MDRecommend")
    @Test
    void mdRecommend() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/shop/main/3"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}