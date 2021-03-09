package com.kurlabo.backend.security.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

// JWT를 위한 커스텀 필터
public class JwtFilter extends GenericFilterBean {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String HEADER_PREFIX = "Bearer ";
    private final TokenProvider tokenProvider;
    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    // JWT 토큰의 인증정보를 현재 실행중인 SecurityContext에 저장하는 역할 수행
   @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String jwt = resolveToken(httpServletRequest);                              // Request에서 토큰 받아옴
        String requestURI = httpServletRequest.getRequestURI();
        // 토큰의 유효성 검증을 하는 메소드(validateToken)를 통과시킴
        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);   // 토큰이 정상이면 Authentication 객체를 받아옴
            SecurityContextHolder.getContext().setAuthentication(authentication);   // 받아온 Authentication 객체응 SecurityContext에 저장
            logger.debug("Security Context에 '{}' 인증 정보를 저장했습니다, uri: {}", authentication.getName(), requestURI);
        } else {
            logger.debug("유요한 JWT 토큰이 없습니다, uri: {}", requestURI);
        }
        chain.doFilter(request, response);
    }

    // Request에서 토큰 정보를 꺼내오기 위한 메소드드
   private String resolveToken(HttpServletRequest request) {
        // Request에서 토큰 정보를 꺼내옴
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(HEADER_PREFIX)) {
            return bearerToken.substring(HEADER_PREFIX.length());
        }
        return null;
    }
}