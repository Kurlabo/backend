//package com.kurlabo.backend.service;
//
//import com.kurlabo.backend.dto.LoginDto;
//import com.kurlabo.backend.exception.InvalidTokenException;
//import com.kurlabo.backend.repository.MemberRepository;
//import com.kurlabo.backend.security.jwt.JwtTokenProvider;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.http.HttpServletRequest;
//import java.time.Duration;
//
///**
// * 내가 구현 한 부분
// */
//@Service
//@RequiredArgsConstructor
//public class LoginService {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final AuthenticationManagerBuilder authenticationManagerBuilder;
//    private final MemberRepository memberRepository;
//    private final MemberService memberService;
//    private final RedisTemplate<String, String> redisTemplate;
//
//    @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds;
//
//    public String login(LoginDto loginDto) {
//
//        UsernamePasswordAuthenticationToken authenticationToken =
//                new UsernamePasswordAuthenticationToken(loginDto.getUid(), loginDto.getPassword());
//
//        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return jwtTokenProvider.createToken(authentication);
//    }
//
//    public void logout(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//        valueOperations.set(token, token, Duration.ofSeconds(tokenValidityInSeconds));
//    }
//
//    public void isInTheInvalidTokenList(HttpServletRequest request) {
//        String token = request.getHeader("Authorization");
//        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
//
//        if (StringUtils.hasText(valueOperations.get(token))) {
//            throw new InvalidTokenException("유효하지 않은 토큰으로 접근했습니다.");
//        }
//    }
//
//}
