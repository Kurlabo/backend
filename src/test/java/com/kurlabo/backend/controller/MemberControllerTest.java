package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.model.Member;
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

    @BeforeEach
    void getToken() {
        LoginDto loginDto = new LoginDto();
        loginDto.setUid("employee");
        loginDto.setPassword("aaaa123123");
        token = loginService.login(loginDto);
        token1 = token.getToken();
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
                .header("Authorization", token1)
                .content(
                        objectMapper.writeValueAsString(
                                LoginDto.builder()
                                        .uid("employee")
                                        .password("aaaa123123")
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

    @DisplayName("findPw")
    @Test
    void findPw() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/find_pw")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                FindPwDto.builder()
                                        .name("개명함")
                                        .uid("employee")
                                        .email("empl22@gmail.com")
                                        .password("변경할패스워드")
                                        .build()
                        )
                ))
                .andExpect(jsonPath("$.message").value("SUCCESS"))

                //.andExpect(jsonPath("$.email").value("limnoa*******@gmail.com"))
                .andExpect(status().isOk());
    }

    @DisplayName("getMemberInfoTest")
    @Test
    void getMemberInfoTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/member/myinfo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token1)
                .content(
                        objectMapper.writeValueAsString(
                                MemberDto.builder()
                                        .uid("employee")
                                        .name("수정된사용자")
                                        .email("empl22@gmail.com")
                                        .gender("선택안함")
                                        .phone("01023456778")
                                        .date_of_birth(null)
                                        .build()
                        )
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("사용자정보수정 테스트")
    @Test
    void updateMemberTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/member/myinfo")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .header("Authorization", token1)
                .content(
                        objectMapper.writeValueAsString(
                                MemberDto.builder()
                                        .uid("employee")
                                        .password("aaaa123123")
                                        .name("개명함")
                                        .email("empl22@gmail.com")
                                        .gender("선택안함")
                                        .phone("01001938275")
                                        .build()
                        )
                ))
                .andExpect(status().isOk());
    }

    @DisplayName("번호중복 테스트")
    @Test
    void checkPhoneTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/member/checkPhone")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(
                        objectMapper.writeValueAsString(
                                new CheckPhoneDto("01012345678")
                        )))
                .andExpect(status().isOk())
                .andExpect(content().string("EXISTED PHONE NUMBER"));
    }

}