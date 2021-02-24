//package com.kurlabo.backend.security.jwt;
//
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * 유요한 자격증명을 제공하지 ㅇ낳고 접근하려 할때 401 UNAUTHROIZED 에러를 리
// */
//@Component
//public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//    @Override
//    public void commence(HttpServletRequest request,
//                         HttpServletResponse response,
//                         AuthenticationException authException) throws IOException {
//
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
//
//    }
//}
