package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.member.LoginDto;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@Transactional
@RequiredArgsConstructor
public class LoginService {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds;
    public String login(LoginDto loginDto) {
        checkMemberStatus(loginDto);
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUid(), loginDto.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return tokenProvider.createToken(authentication);
    }
    private void checkMemberStatus(LoginDto loginDto) {
        Member member = memberRepository
                .findByUid(loginDto.getUid())
                .orElseThrow(() -> new DataNotFoundException("로그인할 멤버가 존재하지 않습니다."));
    }
    public void logout(String token) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(token, token, Duration.ofSeconds(tokenValidityInSeconds));
    }
}