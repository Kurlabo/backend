package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.config.security.JwtTokenProvider;
import com.kurlabo.backend.dto.MemberDto;
import com.kurlabo.backend.dto.testdto.FindIdTestDto;
import com.kurlabo.backend.dto.testdto.FindPwdTestDto;
import com.kurlabo.backend.dto.testdto.MemberTestDto;
import com.kurlabo.backend.service.MemberService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MemberControllerTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    MemberService memberService;

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

    @DisplayName("회원가입 테스트")
    @Test
    public void signupTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/signup")
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
                                        .grade("일반")
                                        .date_of_birth(null)
                                        .build()
                        )
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("회원조회 테스트")
    @Test
    public void getMemberTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/myinfo")
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
                                        .date_of_birth(null)
                                        .build()
                        )
                ))
                .andExpect(status().isOk());

    }

    @DisplayName("회원수정 테스트")
    @Test
    public void updateMemberTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/member/{member_id}")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                MemberDto.builder()
                                        .email("test01@fastcampus.com")
                                        .password("test")
                                        .name("Tester")
                                        .phone("010-1111-2222")
                                        .gender("남자")
                                        .date_of_birth(null)
                                        .build()
                        )
                ))
                .andExpect(status().isOk());
    }




//    @DisplayName("MyinfoTest")
//    @Test
//    void myinfoTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/myinfo"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.uid").value("lnoah"))
//                .andExpect(jsonPath("$.password").value("fastcampus123"))
//                .andExpect(jsonPath("$.name").value("임정우"))
//                .andExpect(jsonPath("$.email").value("lnoah@fastcampus.com"))
//                .andExpect(jsonPath("$.phone").value("010-4321-5678"))
//                .andExpect(jsonPath("$.address").value("서울시 성동구 성수길 77"))
//                .andExpect(jsonPath("$.gender").value("남자"))
//                .andExpect(jsonPath("$.date_of_birth").value("1991-03-01"));
//    }
//
//    @DisplayName("FindIdTest")
//    @Test
//    void findIdTest() throws Exception {
//
//        String content = objectMapper.writeValueAsString(new FindIdTestDto("임정우", "lnoah@fastcampus.com"));
//
//        mockMvc.perform(post("/api/member/find_id").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value("고객님의 아이디 찾기가 완료되었습니다!"));
//
//    }
//
//    @DisplayName("FindPwdTest")
//    @Test
//    void findPwdTest() throws Exception {
//
//        String content = objectMapper.writeValueAsString(new FindPwdTestDto("임정우", "lnoah", "lnoah@fastcampus.com"));
//
//        mockMvc.perform(post("/api/member/find_pwd").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value("고객님의 비밀번호가 이메일로 발송되었습니다!"));
//    }
//
//    @Test
//    @DisplayName("MemberLoginTest")
//    public void loginTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/login")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(
//                        MemberTestDto.builder()
//                                .uid("userAccount")
//                                .password("userpassword111")
//                                .build()
//                        )
//                ))
//                .andExpect(status().isOk())
//                .andDo(print());
//    }
}
