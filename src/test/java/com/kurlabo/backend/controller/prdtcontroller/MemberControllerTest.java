package com.kurlabo.backend.controller.prdtcontroller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.MemberDto;
import com.kurlabo.backend.dto.testdto.MemberTestDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MemberControllerTest {
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("Join Test")
    @Test
    public void joinTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/join")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                MemberDto.builder()
                                .uid("Tester01")
                                .password("test")
                                .name("Tester")
                                .email("test01@fastcampus.com")
                                .phone("010-1111-2222")
                                .gender("남자")
                                .date_of_birth("1991-03-01")
                                .build()
                        )
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("getMember Test")
    @Test
    public void getMemberTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/{member_id}}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                MemberDto.builder()
                                .uid("lnoah")
                                .password("fastcampus123")
                                .name("임정우")
                                .email("lnoah@fastcampus.com")
                                .phone("010-4321-5678")
                                .gender("남자")
                                .date_of_birth("1991-03-01")
                                .build()
                        )
        ))
        .andExpect(status().isOk());

    }

    @Test
    public void editMemberTest() throws Exception {

    }
}
