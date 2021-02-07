package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.MyinfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/member")
public class MemberController {

    @GetMapping("/myinfo")
    public MyinfoDto myinfoTest(){
        MyinfoDto dummyDto = new MyinfoDto();

        dummyDto.setUid("lnoah");
        dummyDto.setPassword("fastcampus123");
        dummyDto.setName("임정우");
        dummyDto.setEmail("lnoah@fastcampus.com");
        dummyDto.setPhone("010-4321-5678");
        dummyDto.setAddress("서울시 성동구 성수길 77");
        dummyDto.setGender("남자");
        dummyDto.setDate_of_birth("1991-03-01");

        return dummyDto;
    }

}
