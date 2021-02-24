//package com.kurlabo.backend.controller;
//
//import com.kurlabo.backend.dto.LoginDto;
//import com.kurlabo.backend.dto.TokenDto;
//import com.kurlabo.backend.security.jwt.JwtFilter;
//import com.kurlabo.backend.service.LoginService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
///**
// * 내가 만든 부분. 예찬이네 세팅으로 리팩토링 필요.
// */
//
//@CrossOrigin
//@RestController
//@RequestMapping("/api/member")
//@RequiredArgsConstructor
//public class LoginController {
//
//    private final LoginService loginService;
//
//    @PostMapping("/login")
//    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
//
//        String jwt = loginService.login(loginDto);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, JwtFilter.HEADER_PREFIX + jwt);
//
//        return new ResponseEntity<>(new TokenDto(jwt), httpHeaders, HttpStatus.OK);
//    }
//
//    @GetMapping("/logout")
//    @PreAuthorize("authenticated")
//    public ResponseEntity<String> logout(HttpServletRequest servletRequest) {
//        loginService.isInTheInvalidTokenList(servletRequest);
//        loginService.logout(servletRequest);
//
//        return new ResponseEntity<>("로그아웃하셨습니다", HttpStatus.OK);
//    }
//}
