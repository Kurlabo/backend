package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.security.jwt.JwtFilter;
import com.kurlabo.backend.security.jwt.TokenProvider;
import com.kurlabo.backend.service.LoginService;
import com.kurlabo.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/member")
public class MemberController {

    private final MemberService memberService;
    private final LoginService loginService;
    private final TokenProvider tokenProvider;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody MemberDto requestDto) {
        return ResponseEntity.ok(memberService.signUp(requestDto));
    }

    @PostMapping(value = "/signup/check-uid")
    public ResponseEntity<?> checkUid(@Valid @RequestBody CheckUidDto requestDto) {
        return ResponseEntity.ok(memberService.findAllByUid(requestDto));
    }

    @PostMapping(value = "/signup/check-email")
    public ResponseEntity<?> CheckEmail(@Valid @RequestBody CheckEmailDto requestDto) {
        return ResponseEntity.ok(memberService.findAllByEmail(requestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login (@Valid @RequestBody LoginDto requestDto, HttpServletResponse response) {
        TokenDto dto = loginService.login(requestDto);

        Cookie cookie = new Cookie("refreshToken", dto.getAccessToken());
        cookie.setMaxAge(5 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setDomain("kurlabo.cf");

        response.addCookie(cookie);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + dto.getAccessToken());
        return new ResponseEntity<>(dto, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/logout")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(loginService.logout(token));
    }

    @PostMapping("/find-id")
    public ResponseEntity<?> findId(@Valid @RequestBody FindIdDto requestDto) {
        return ResponseEntity.ok(memberService.findByNameAndEmail(requestDto));
    }

    @PostMapping("/find-pw")
    public ResponseEntity<?> findPw(@Valid @RequestBody FindPwDto requestDto) {
        return ResponseEntity.ok(memberService.findByNameAndUidAndEmail(requestDto));
    }

    @PatchMapping("/find-pw")
    public ResponseEntity<?> findPwChange(@Valid @RequestBody FindPwChangeDto requestDto){
        return ResponseEntity.ok(memberService.setPassword(requestDto));
    }

    @PostMapping(value = "/check-phone")
    public ResponseEntity<?> checkPhone(@Valid @RequestBody CheckPhoneDto requestDto) {
        return ResponseEntity.ok(memberService.findByPhone(requestDto));
    }

    @PostMapping(value = "/my-info")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> checkMemberPassword(@RequestHeader("Authorization") String token,
                                                 @Valid @RequestBody CheckPwDto requestDto) {
        return ResponseEntity.ok(memberService.checkPassword(tokenProvider.parseTokenToGetMemberId(token), requestDto));
    }

    @GetMapping(value = "/my-info")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> getMemberInfo(@RequestHeader("Authorization") String token) {
        return ResponseEntity.ok(memberService.getMemberInfo(tokenProvider.parseTokenToGetMemberId(token)));
    }

    @PutMapping(value = "/my-info")
    @PreAuthorize("authenticated")
    public void updateMemberInfo(@RequestHeader("Authorization") String token,
                                 @Valid @RequestBody MemberDto requestDto) {
        memberService.updateMember(tokenProvider.parseTokenToGetMemberId(token), requestDto);
    }

    @DeleteMapping(value = "/my-info")
    @PreAuthorize("authenticated")
    public void deleteMember (@RequestHeader("Authorization") String token) {
        memberService.deleteMember(tokenProvider.parseTokenToGetMemberId(token));
        loginService.logout(token);
    }
}