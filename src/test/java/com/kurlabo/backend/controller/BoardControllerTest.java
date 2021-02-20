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
public class BoardControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @DisplayName("공지사항 리스트")
    @Test
    void getBoardList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/list").param("page", String.valueOf(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(4))
                .andExpect(jsonPath("$.content[0].title").value("주문취소마감 시간 변경 공지"))
                .andExpect(jsonPath("$.content[0].writer").value("MarketKurly"))
                .andExpect(jsonPath("$.content[0].cnt").value(7190))
                .andExpect(jsonPath("$.content[1].id").value(5))
                .andExpect(jsonPath("$.content[1].title").value("[공지]동절기 포장 적용 안내(11/28 수령건~)"))
                .andExpect(jsonPath("$.content[1].writer").value("MarketKurly"))
                .andExpect(jsonPath("$.content[1].cnt").value(2223));
    }

    @DisplayName("공지사항 상세조회")
    @Test
    void getBoard() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/board/view/3"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title").value("에코박스 도입에 따른 한시적 포장 방법 변경 공지(종료 시점 확정)"))
                .andExpect(jsonPath("$.writer").value("MarketKurly"))
//                .andExpect(jsonPath("$.regdate").value(LocalDate.of(2017,4,6)))   //같은 LocalDate인데 매칭이 안되는 이유는?
                .andExpect(jsonPath("$.cnt").value(2828));
    }
}
