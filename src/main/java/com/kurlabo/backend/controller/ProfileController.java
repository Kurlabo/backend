package com.kurlabo.backend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {
    private final Environment environment;

    @GetMapping("/profile")
    public String profile(){
        // 현재 실행 중인 ActiveProfile을 모두 가져온다.
        // 즉, real, oauth, real-db 등이 활성화 되어 있다면 (active) 3개가 모두 담겨 있다.
        // 여기서 real, real1, real2는 모두 배포에 사용될 profile이라 이 중 하나라도 있으면 그 값을 반환하도록 한다.
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        System.out.println(profiles);
        System.out.println(realProfiles);
        System.out.println(defaultProfile);

        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
    }
}