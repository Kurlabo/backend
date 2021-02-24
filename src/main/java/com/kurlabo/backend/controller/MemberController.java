package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.exception.UserNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.service.MemberService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberRepository memberRepository; // ?

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto signupRequestDto) {

        String token = memberService.signUp(signupRequestDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }

    /////////////////////
//    @GetMapping(value = "/myinfo")
//    public ResponseEntity<?> getMember (@Valid @RequestBody GetMemberRequestDto getMemberRequestDto) {
//
//        String token = memberService.getMember(getMemberRequestDto);
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(token);
//    }

//    @GetMapping(value = "/myinfo")
//    public UserInfo getMember(@Valid @RequestBody GetMemberRequestDto getMemberRequestDto,
//                              Authentication authentication) {
//        String uid = authentication.getName();
//        Member member = memberRepository
//                .findByUid(uid).orElseThrow(() -> new UserNotFoundException("회원이 존재하지 않습니다."));
//
//        return UserInfo.builder()
//                .name(member.getName())
//                .email(member.getEmail())
//                .phone(member.getPhone())
//                .gender(member.getGender())
//                .date_of_birth(member.getDate_of_birth())
//                .build();
//    }
//
//    @Data
//    @AllArgsConstructor
//    @NoArgsConstructor
//    @Builder
//    public static class UserInfo {
//        private String name;
//        private String email;
//        private String phone;
//        private String gender;
//        private LocalDate date_of_birth;
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping(value = "/myinfo")
    public UpdateMemberResponseDto updateMember(@Valid @RequestBody UpdateMemberRequestDto updateMemberRequestDto,
                                                Authentication authentication) {

        log.debug("authentication >> {}", authentication);

        String uid = authentication.getName();

        if (memberRepository.existsByUid(uid)) {
            throw new UserNotFoundException("존재하지 않는 회원입니다 {" + uid + "}");
        }

        Member updatedMember = memberService.updateUser(updateMemberRequestDto, uid);
        String token = memberService.getAccessToken(updatedMember);

        return UpdateMemberResponseDto.builder()
                .accessToken(token)
                .build();
    }


    /////////////////////////////////////////////////////// Ryo fukui
// todo for developing
//
//    @DeleteMapping(value = "/myinfo/{member_id}")
//    public ResponseEntity<Boolean> deleteMember (DeleteMemberRequestDto deleteMemberRequestDto, Authentication authentication) {
//
//        String uid = authentication.getName();
//
//        if (!memberRepository.existsByUid(uid)) {
//            throw new UserNotFoundException("이미 탈퇴한 회원입니다. {" + uid + "}");
//        }
//
//
//        Member deletedMember = memberService.deleteUser(deleteMemberRequestDto);
//        String token = memberService.getAccessToken(deletedMember);
//
//        return DeleteMemberResponseDto.builder()
//                .accessToken(token)
//                .build();
//
//    }



    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        return memberService.login(loginRequestDto);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(@RequestBody LogoutRequestDto logoutRequestDto) {
//        return memberService.logout(logoutRequestDto);
//    }
}


