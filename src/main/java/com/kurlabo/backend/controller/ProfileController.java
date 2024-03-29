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
        List<String> profiles = Arrays.asList(environment.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        StringBuilder sb = new StringBuilder("적용된 profiles : ");
        for(String profileList : profiles){
            sb.append(profileList).append(", ");
        }
        System.out.println(sb.deleteCharAt(sb.length() - 2).toString());

        return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
    }
}