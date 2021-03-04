package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.exception.CUserNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.service.LoginService;
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

    private final MemberService memberService;
    private final LoginService loginService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody MemberDto dto) {
        return ResponseEntity.ok(memberService.signUp(dto));
    }

    @PostMapping(value = "/signup/checkuid")
    public ResponseEntity<?> checkUid(@Valid @RequestBody CheckUidDto dto) {
        return ResponseEntity.ok(memberService.checkUid(dto));
    }

    @PostMapping(value = "/signup/checkemail")
    public ResponseEntity<?> checkUid(@Valid @RequestBody CheckEmailDto dto) {
        return ResponseEntity.ok(memberService.checkEmail(dto));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@Valid @RequestBody LoginDto loginDto) {
        String token = loginService.login(loginDto);
        return ResponseEntity.ok(new TokenDto(token));
    }
}