package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.dto.testdto.TestInfoDto;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.security.jwt.JwtFilter;
import com.kurlabo.backend.service.LoginService;
import com.kurlabo.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<TokenDto> login (@Valid @RequestBody LoginDto loginDto) {
        TokenDto dto = loginService.login(loginDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + dto.getToken());
        return new ResponseEntity<>(dto, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/logout")
    @PreAuthorize("authenticated")
    public void logout(@RequestHeader("Authorization") String token){
        loginService.logout(token);
    }

    @PostMapping("/find_id")
    public ResponseEntity<?> findId(@Valid @RequestBody FindIdDto findIdDto) {
        return ResponseEntity.ok(memberService.findId(findIdDto));
    }

    @PostMapping("/find_pw")
    public ResponseEntity<?> findPw(@Valid @RequestBody FindPwDto findPwDto) {
        return ResponseEntity.ok(memberService.findPw(findPwDto));
    }

    @GetMapping("/testgetuserinfo")
    public ResponseEntity<?> userinfo(@RequestHeader("Authorization") String token){
        System.out.println("token >>>>>>>>>>>>>>>>>> " + token);
        return ResponseEntity.ok(loginService.testInfo(token));
    }
}