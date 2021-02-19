package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.MainTestDto;
import com.kurlabo.backend.service.MainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    @GetMapping("/main?{category}")
    public ResponseEntity<?> mdRecommend(@PathVariable int category){
        return ResponseEntity.ok(null);
    }
}
