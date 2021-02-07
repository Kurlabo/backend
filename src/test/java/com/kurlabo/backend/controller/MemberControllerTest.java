package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.testdto.MyinfoDto;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    @DisplayName("MyinfoTest")
    @Test
    void myinfoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/myinfo"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uid").value("lnoah"))
                .andExpect(jsonPath("$.password").value("fastcampus123"))
                .andExpect(jsonPath("$.name").value("임정우"))
                .andExpect(jsonPath("$.email").value("lnoah@fastcampus.com"))
                .andExpect(jsonPath("$.phone").value("010-4321-5678"))
                .andExpect(jsonPath("$.address").value("서울시 성동구 성수길 77"))
                .andExpect(jsonPath("$.gender").value("남자"))
                .andExpect(jsonPath("$.date_of_birth").value("1991-03-01"));
    }
}