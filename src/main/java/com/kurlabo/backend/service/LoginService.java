package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.MessageResponseDto;
import com.kurlabo.backend.dto.member.LoginDto;
import com.kurlabo.backend.dto.member.TokenDto;
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
    private final RedisTemplate<String, String> redisTemplate;
    @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds;

    public TokenDto login(LoginDto loginDto) {
        checkMemberStatus(loginDto);
        // UID와 Password를 받아 UsernamePasswordAuthenticationToken을 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUid(), loginDto.getPassword());
        // authenticationToken을 이용해 Authentication 객체를 생성하기 위해 authenticate가 실행이 될 때 loadUserByUsername 메소드가 실행됨. 이 결과 값으로 Authentication 객체가 생성됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // Authentication 객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // tokenProvider의 createToken 메소드를 통해서 JWT Token을 생성해서 리턴
        String jwt = tokenProvider.createToken(authentication);

        return new TokenDto(jwt);
    }

    public MessageResponseDto logout(String token) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(token, token, Duration.ofSeconds(tokenValidityInSeconds));
        return MessageResponseDto.builder().message("LOGOUT SUCCESS").build();
    }
    
    private void checkMemberStatus(LoginDto loginDto) {
        Member member = memberRepository.findByUid(loginDto.getUid())
                .orElseThrow(() -> new DataNotFoundException("로그인할 멤버가 존재하지 않습니다."));
    }
}