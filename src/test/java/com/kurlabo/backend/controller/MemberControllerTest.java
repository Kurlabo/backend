package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.member.CheckEmailDto;
import com.kurlabo.backend.dto.member.CheckUidDto;
import com.kurlabo.backend.dto.member.MemberDto;
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

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @DisplayName("SignUp")
    @Test
    void signUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/signup").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(new MemberDto(
                                "lim0301",
                                "jamesrodriguez0301@gmail.com",
                                "james910301",
                                "james910301",
                                "임정우",
                                "01066075331",
                                "남자",
                                LocalDate.of(1991,03,01),
                                "서울시 강동구 암사동 룰루랄라아파트 103동 103호"
                        ))))
                .andExpect(status().isOk());
    }

    @DisplayName("CheckUid")
    @Test
    void checkUid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/signup/checkuid").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(new CheckUidDto("lnoah")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string("EXISTED UID"));
    }

    @DisplayName("CheckEmail")
    @Test
    void checkEmail() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/signup/checkemail").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(new CheckEmailDto("noah123123@fastcampus.com")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string("NOT EXISTED EMAIL"));
    }
}