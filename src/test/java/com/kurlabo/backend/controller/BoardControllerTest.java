package com.kurlabo.backend.controller;

import org.junit.jupiter.api.BeforeEach;
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
public class BoardControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void read() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/board"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.board_id").value(1L))
                .andExpect(jsonPath("$.title").value("공지사항 제목"))
                .andExpect(jsonPath("$.writer").value("작성자"))
                .andExpect(jsonPath("$.cnt").value(0));
    }

    @Test
    void getRead() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/board/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.board_id").value(1L))
                .andExpect(jsonPath("$.title").value("공지사항 제목1"))
                .andExpect(jsonPath("$.writer").value("작성자"))
                .andExpect(jsonPath("$.cnt").value(1))
                .andExpect(jsonPath("$.content").value("공지사항 내용1"));

    }
}
