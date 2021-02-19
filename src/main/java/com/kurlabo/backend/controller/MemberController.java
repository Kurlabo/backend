package com.kurlabo.backend.controller;

import com.kurlabo.backend.config.security.CurrentUser;
import com.kurlabo.backend.config.security.JwtTokenProvider;
import com.kurlabo.backend.exception.CUserNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.nio.file.attribute.UserPrincipal;

//@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/member")
public class MemberController {
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/signup")
    public SignUpRes signup(@Valid @RequestBody SignUpReq signUpReq) {
        Member member = Member.builder()
                .uid(signUpReq.getUid())
                .email(signUpReq.getEmail())
                .password(passwordEncoder.encode(signUpReq.getPassword()))
                .name(signUpReq.getName())
                .phone(signUpReq.getPhone())
                .gender(signUpReq.getGender())
                .grade(signUpReq.getGrade())
                .build();

        Member savedMember = memberRepository.save(member);
        String token = jwtTokenProvider.createAccessToken(String.valueOf(savedMember.getId()), "USER");

        return SignUpRes.builder()
                .accessToken(token)
                .build();
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SignUpReq {
        private String uid;
        private String email;
        private String password;
        private String name;
        private String phone;
        private String gender;
        private String grade;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SignUpRes {
        private String accessToken;
    }

    @GetMapping
    public UserInfo me(Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository.findByEmail(email).orElseThrow(CUserNotFoundException::new);

        UserInfo userInfo = UserInfo.builder()
                .name(member.getName())
                .build();

        return userInfo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserInfo {
        private String name;
    }
   /* @GetMapping("/myinfo")
    public ResponseEntity<?> myinfoTest(){
        MyinfoTestDto dummyDto = new MyinfoTestDto();

        dummyDto.setUid("lnoah");
        dummyDto.setPassword("fastcampus123");
        dummyDto.setName("임정우");
        dummyDto.setEmail("lnoah@fastcampus.com");
        dummyDto.setPhone("010-4321-5678");
        dummyDto.setAddress("서울시 성동구 성수길 77");
        dummyDto.setGender("남자");
        dummyDto.setDate_of_birth("1991-03-01");

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(dummyDto);
    }

    @PostMapping("/find_id")
    public ResponseEntity<?> findIdTest(@RequestBody FindIdTestDto findIdTestDto){
        String dbMemberName = "임정우";
        String dbMemberEmail = "lnoah@fastcampus.com";
        String msg = "";

        System.out.println("findIdDto.name >>>> " + findIdTestDto.getName() + ", findIdDto.email >>>> " + findIdTestDto.getEmail());
        System.out.println("dbMemberName   >>>> " + dbMemberName + ", dbMemberEmail   >>>> " + dbMemberEmail);

        if(!findIdTestDto.getName().equals(dbMemberName) || !findIdTestDto.getEmail().equals(dbMemberEmail)){
            msg = "고객님께서 입력하신 정보가 정확한지 확인 후 다시 시도해주세요.";
        } else {
            msg = "고객님의 아이디 찾기가 완료되었습니다!";
        }

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(msg);
    }

    @PostMapping("/find_pwd")
    public ResponseEntity<?> findPwdTest(@RequestBody FindPwdTestDto findPwdTestDto){

        String dbMemberName = "임정우";
        String dbMemberUid = "lnoah";
        String dbMemberEmail = "lnoah@fastcampus.com";
        String msg = "";

        if(!findPwdTestDto.getName().equals(dbMemberName) || !findPwdTestDto.getUid().equals(dbMemberUid) || !findPwdTestDto.getEmail().equals(dbMemberEmail)){
            msg = "사용자 정보가 존재하지 않습니다";
        } else {
            msg = "고객님의 비밀번호가 이메일로 발송되었습니다!";
        }

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(msg);
    }

    // 회원가입
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody MemberTestDto memberTestDto) {

        memberTestDto.setMemberId(1L);
        memberTestDto.setUid("userAccount");
        memberTestDto.setName("userName!");
        memberTestDto.setEmail("userAccount@gmail.com");
        memberTestDto.setDateOfBirth("2020-02-02");
        memberTestDto.setPhone("010-1111-2222");
        memberTestDto.setPassword("password");
        memberTestDto.setGender("1");

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(memberTestDto);
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody MemberTestDto memberTestDto) {

        String message = "";
        String id = "userAccount";
        String pwd = "userpassword";

        if (!memberTestDto.getUid().equals(id)) {
            message = "아이디 또는 비밀번호 오류입니다.";
        } else if (!memberTestDto.getPassword().equals(pwd)) {
            message = "아이디 또는 비밀번호 오류입니다.";
        } else {
            message = "로그인 성공";
        }

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(message);
    }*/

}
