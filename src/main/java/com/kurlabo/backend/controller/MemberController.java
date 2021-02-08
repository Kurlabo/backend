package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/member")
public class MemberController {

    @GetMapping("/myinfo")
    public MyinfoDto myinfoTest(){
        MyinfoDto dummyDto = new MyinfoDto();

        dummyDto.setUid("lnoah");
        dummyDto.setPassword("fastcampus123");
        dummyDto.setName("임정우");
        dummyDto.setEmail("lnoah@fastcampus.com");
        dummyDto.setPhone("010-4321-5678");
        dummyDto.setAddress("서울시 성동구 성수길 77");
        dummyDto.setGender("남자");
        dummyDto.setDate_of_birth("1991-03-01");

        return dummyDto;
    }

    @PostMapping("/find_id")
    public String findIdTest(@RequestBody FindIdDto findIdDto){

        System.out.println(">>>> " + findIdDto);

        String dbMemberName = "임정우";
        String dbMemberEmail = "lnoah@fastcampus.com";
        String msg = "";

        if(findIdDto.getName() != dbMemberName || findIdDto.getEmail() != dbMemberEmail){
            msg = "고객님께서 입력하신 정보가 정확한지 확인 후 다시 시도해주세요.";
        } else {
            msg = "고객님의 아이디 찾기가 완료되었습니다!";
        }

        return msg;
    }

    @PostMapping("/find_pwd")
    public String findPwdTest(@RequestBody FindPwdDto findPwdDto){

        String dbMemberName = "임정우";
        String dbMemberUid = "lnoah";
        String dbMemberEmail = "lnoah@fastcampus.com";
        String msg = "";

        if(findPwdDto.getName() != dbMemberName || findPwdDto.getUid() != dbMemberUid || findPwdDto.getEmail() != dbMemberEmail){
            msg = "사용자 정보가 존재하지 않습니다";
        } else {
            msg = "고객님의 비밀번호가 이메일로 발송되었습니다!";
        }

        return msg;
    }

    // 회원가입
    @PostMapping("/join")
    public MemberDto join(@RequestBody MemberDto memberDto) {

        memberDto.setMemberId(1L);
        memberDto.setUid("userAccount");
        memberDto.setName("userName!");
        memberDto.setEmail("userAccount@gmail.com");
        memberDto.setDateOfBirth("2020-02-02");
        memberDto.setPhone("010-1111-2222");
        memberDto.setPassword("password");
        memberDto.setGender("1");

        return memberDto;
    }

    // 로그인
    @PostMapping("/login")
    public String login(@RequestBody MemberDto memberDto) {

        String message = "";
        String id = "userAccount";
        String pwd = "userpassword";

        if (!memberDto.getUid().equals(id)) {
            message = "아이디 또는 비밀번호 오류입니다.";
        } else if (!memberDto.getPassword().equals(pwd)) {
            message = "아이디 또는 비밀번호 오류입니다.";
        } else {
            message = "로그인 성공";
        }

        return message;
    }

}
