package com.kurlabo.backend.controller;

import com.kurlabo.backend.config.security.JwtTokenProvider;
import com.kurlabo.backend.dto.member.LoginRequestDto;
import com.kurlabo.backend.dto.member.LoginResponseDto;
import com.kurlabo.backend.dto.member.SignupRequestDto;
import com.kurlabo.backend.dto.member.SignupResponseDto;
import com.kurlabo.backend.exception.CUserNotFoundException;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.LoginFailException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.service.MemberService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

//@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping(value = "/signup")
    public SignupResponseDto signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        Member member = Member.builder()
                .uid(signupRequestDto.getUid())
                .email(signupRequestDto.getEmail())
                .password(passwordEncoder.encode(signupRequestDto.getPassword()))
                .name(signupRequestDto.getName())
                .phone(signupRequestDto.getPhone())
                .gender(signupRequestDto.getGender())
                .date_of_birth(signupRequestDto.getDate_of_birth())
                .grade(signupRequestDto.getGrade())
                .build();

        Member savedMember = memberRepository.save(member);
        String token = jwtTokenProvider.createAccessToken(String.valueOf(savedMember.getId()), "USER");

        return SignupResponseDto.builder()
                .accessToken(token)
                .build();
    }

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class SignUpReq {
//        private String uid;
//        private String email;
//        private String password;
//        private String name;
//        private String phone;
//        private String gender;
//        private LocalDate date_of_birth;
//        private String grade;
//    }

//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class SignUpRes {
//        private String accessToken;
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping (value = "/myinfo")
    public UserInfo getMember(Authentication authentication) {
        String email = authentication.getName();
        Member member = memberRepository
                .findByEmail(email).orElseThrow(() -> new CUserNotFoundException("회원이 존재하지 않습니다."));

        UserInfo userInfo = UserInfo.builder()
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .gender(member.getGender())
                .date_of_birth(member.getDate_of_birth())
                .build();

        return userInfo;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserInfo {
        private String name;
        private String email;
        private String phone;
        private String gender;
        private LocalDate date_of_birth;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    @PutMapping(value = "/{member_id}")
//    public UpdateMemberRes updateMember(@Valid @RequestBody UpdateMemberReq updateMemberReq) {
//
//        Member member = memberRepository
//                .findByEmail(email).orElseThrow(() -> new CUserNotFoundException("회원이 존재하지 않습니다."));
//
//        Member updatedMember = memberRepository.save(member);
//        String token = jwtTokenProvider.createAccessToken(String.valueOf(updatedMember.getId()), "USER");
//
//        return UpdateMemberRes.builder()
//                .accessToken(token)
//                .build();
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class UpdateMemberReq {
//        private String email;
//        private String password;
//        private String name;
//        private String phone;
//        private String gender;
//        private LocalDate date_of_birth;
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class UpdateMemberRes {
//        private String accessToken;
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    @DeleteMapping(value = "{/member_id}")
//    public DeleteMemberRes deleteMember(@Valid @RequestBody DeleteMemberReq deleteMemberReq) {
//        Member member = Member.builder()
//                .id(deleteMemberReq.getId())
//                .build();
//
//        Member deleteMember = memberRepository.findById()
//                .orElseThrow(()-> new DataNotFoundException("회원이 존재하지 않습니다."));
//
//        member.
//
//        Member deletedMember = memberRepository.save(deleteMember);
//
//        String token = jwtTokenProvider
//                .createAccessToken(String.valueOf(deleteMember.getId()), "USER");
//
//        return DeleteMemberRes.builder()
//                .accessToken(token)
//                .build();
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    public static class DeleteMemberReq {
//        private Long id;
//    }
//
//    @Data
//    @NoArgsConstructor
//    @AllArgsConstructor
//    @Builder
//    public static class DeleteMemberRes {
//        private String accessToken;
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        Member member = memberService.findByUid(loginRequestDto.getUid());

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            throw new LoginFailException();
        }

        String accessToken = jwtTokenProvider.createAccessToken(String.valueOf(member.getId()), "USER");
        LoginResponseDto loginRes = LoginResponseDto.builder()
                .accessToken(accessToken)
                .build();

        return ResponseEntity.ok().body(loginRes);
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

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

    }*/

}
