package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.member.*;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/signup").contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(
//                        objectMapper.writeValueAsString(new MemberDto(
//                                "nemnemnemnem",
//                                "tosiaki210304@gmail.com",
//                                "numnumnum2323",
//                                "numnumnum2323",
//                                "최유선",
//                                "01001938275",
//                                "여자",
//                                LocalDate.of(1991,03,01),
//                                "서울시 은평구 은평동 은평아파트 122동 1212호"
//                        ))))
//                .andExpect(status().isOk());
    }

    @DisplayName("CheckUid")
    @Test
    void checkUid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/signup/checkuid").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(new CheckUidDto("nemnemnemnem")
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

    @DisplayName("login")
    @Test
    void loginTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/login")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                LoginDto.builder()
                                        .uid("nemnemnemnem")
                                        .password("numnumnum2323")
                                        .build()
                        )
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("findId")
    @Test
    void findId() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/find_id")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                FindIdDto.builder()
                                        .name("임정우")
                                        .email("limnoah0301@gmail.com")
                                        .build()
                        )
                ))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(jsonPath("$.uid").value("limnoah0***"))
                .andExpect(status().isOk());
    }
}