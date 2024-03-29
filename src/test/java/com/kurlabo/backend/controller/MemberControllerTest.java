package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.service.LoginService;
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

    private TokenDto token;

    private String token1;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    LoginService loginService;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

//    @BeforeEach
//    void getToken() {
//        LoginDto loginDto = new LoginDto();
//        loginDto.setUid("testid3");
//        loginDto.setPassword("mdmdmd333333");
//        token = loginService.login(loginDto);
//        token1 = token.getToken();
//    }

    @DisplayName("SignUp")
    @Test
    void signUp() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/signup").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(new MemberDto(
                                "usun202",
                                "usun202@gmail.com",
                                "aaaa123123",
                                "aaaa123123",
                                "최유선",
                                "01001938275",
                                "선택안함",
                                LocalDate.of(1991,03,01),
                                "세종특별자치시 보듬2로 42",
                                "132동 2305호",
                                0,
                                3
                        ))))
                .andExpect(status().isOk());
    }

    @DisplayName("CheckUid")
    @Test
    void checkUid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/signup/checkuid").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(new CheckUidDto("testid1")
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
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/login")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization", token1)
//                .content(
//                        objectMapper.writeValueAsString(
//                                LoginDto.builder()
//                                        .uid("employee")
//                                        .password("aaaa123123")
//                                        .build()
//                        )
//                ))
//                .andExpect(status().isOk());
    }

    @DisplayName("findId")
    @Test
    void findId() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/find_id")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(
//                        objectMapper.writeValueAsString(
//                                FindIdDto.builder()
//                                        .name("임정우")
//                                        .email("limnoah0301@gmail.com")
//                                        .build()
//                        )
//                ))
//                .andExpect(jsonPath("$.message").value("SUCCESS"))
//                .andExpect(jsonPath("$.uid").value("limnoah0***"))
//                .andExpect(status().isOk());
    }

    @DisplayName("findPw")
    @Test
    void findPw() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/find_pw")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(
//                        objectMapper.writeValueAsString(
//                                FindPwDto.builder()
//                                        .name("개명함")
//                                        .uid("nemnemnemnem")
//                                        .email("empl22@gmail.com")
//                                        .build()
//                        )
//                ))
//                .andExpect(jsonPath("$.message").value("SUCCESS"))
//                .andExpect(jsonPath("$.member_id").value((long)62))
//
//                //.andExpect(jsonPath("$.email").value("limnoa*******@gmail.com"))
//                .andExpect(status().isOk());
    }

    @DisplayName("findPwChange")
    @Test
    void findPwChange() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/find_pw_change")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                FindPwChangeDto.builder()
                                        .member_id((long)1)
                                        .insertChangePw("noahlimnoah0301")
                                        .build()
                        )))
                .andExpect(jsonPath("$.message").value("SUCCESS"))
                .andExpect(status().isOk());

    }

    @DisplayName("checkMemberInfoTest")
    @Test
    void checkMemberInfoTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/myinfo")
//                        .contentType(MediaType.APPLICATION_JSON_VALUE)
//                        .header("Authorization", token1)
//                        .content(objectMapper.writeValueAsString(new CheckPwDto("aaaa123123")))
//        )
//                .andExpect(jsonPath("$.message").value("SUCCESS"))
//                .andExpect(status().isOk());
    }

    @DisplayName("getMemberInfoTest")
    @Test
    void getMemberInfoTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/myinfo")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization", token1))
////                .content(
////                        objectMapper.writeValueAsString(
////                                new CheckPwDto("aaaa123123")
////                                MemberDto.builder()
////                                        .uid("nemnemnemnem")
////                                        .password("ssss123123")
////                                        .name("곽두팔")
////                                        .email("dopal123@gmail.com")
////                                        .gender("선택안함")
////                                        .phone("01001938275")
////                                        .date_of_birth(null)
////                                        .build()
////                        )
////                )
////                )
//                .andExpect(status().isOk());
    }

    @DisplayName("사용자정보수정 테스트")
    @Test
    void updateMemberTest() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.put("/api/member/myinfo")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .header("Authorization", token1)
//                .content(
//                        objectMapper.writeValueAsString(
//                                MemberDto.builder()
//                                        .uid("nemnemnemnem")
//                                        //.CheckPassword("") // 기존 비밀번호
//                                        //.password("") // 변경할 비밀번호
//                                        .name("곽두팔")
//                                        .email("dopal123@gmail.com")
//                                        .gender("선택안함")
//                                        .phone("01001938275")
//                                        .check_sns(3)
//                                        .check_term(0)
//                                        .build()
//                        )
//                ))
//                .andExpect(status().isOk());
    }

    @DisplayName("번호중복 테스트")
    @Test
    void checkPhoneTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/checkPhone")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                new CheckPhoneDto("01033333333")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string("EXISTED PHONE NUMBER"));
    }

}