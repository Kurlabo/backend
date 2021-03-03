package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.member.MemberDto;
import com.kurlabo.backend.exception.CUserNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.service.MemberService;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/member")
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.signUp(dto));
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

}