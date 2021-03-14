package com.kurlabo.backend.controller;

import com.kurlabo.backend.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/shop")
public class MainController {

    private final MainService mainService;

    // 메인 페이지
    @GetMapping("/main")
    public ResponseEntity<?> mainPage(){
        return ResponseEntity.ok(mainService.setMainPage());
    }

    // MD의 추천 페이지
    @GetMapping("/main/{category}")
    public ResponseEntity<?> mdRecommend(@PathVariable int category){
        return ResponseEntity.ok(mainService.setMdRecommend(category));
    }

    // 사이트 헤더
    @GetMapping("/header")
    @PreAuthorize("authenticated")
    public ResponseEntity<?> header(@RequestHeader("Authorization") String token){
        return ResponseEntity.ok(mainService.setHeader(token));
    }
}