//package com.kurlabo.backend.security;
//
//import com.kurlabo.backend.service.MemberService;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import java.nio.file.PathMatcher;
//
//@RequiredArgsConstructor
//@Configuration
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//    private final JwtRequestFilter jwtRequestFilter;
//    private final MemberService memberService; // may be different
//    private final PasswordEncoder passwordEncoder;
//    private final HandlerExceptionResolver handlerExceptionResolver;
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(memberService)
//                .passwordEncoder(passwordEncoder);
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement()
//                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                    .authorizeRequests()
//                        .antMatchers("/api/member/join", "/api/member/join").permitAll()
//                        .antMatchers(HttpMethod.GET, "/exception/**").permitAll()
//                    .anyRequest().hasRole("USER")
//                    .anyRequest().authenticated()
//                .and()
//                    .exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler())
//                .and()
//                    .exceptionHandling()
//                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
//                .and()
//                    .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
//                            UsernamePasswordAuthenticationFilter.class); // jwt token 필터를 id/password 인증 필터 전에 추가.
////                .and()
////                .exceptionHandling()
////                .authenticationEntryPoint(jwtAuthenticationEntryPoint);
//
//
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
//    }
//
//
//}
