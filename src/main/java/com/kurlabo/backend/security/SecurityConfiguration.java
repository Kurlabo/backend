package com.kurlabo.backend.security;

import com.kurlabo.backend.security.jwt.JwtAccessDeniedHandler;
import com.kurlabo.backend.security.jwt.JwtAuthenticationEntryPoint;
import com.kurlabo.backend.security.jwt.JwtSecurityConfiguration;
import com.kurlabo.backend.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // @PreAuthorize 어노테이션을 메소드 단위로 추가하기 위함
@RequiredArgsConstructor
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()       // 토큰을 사용하기 때문에 csrf를 disable

                .exceptionHandling()    // Exception Handling을 할 때 이미 만들었던 클래스들을 추가
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .sessionManagement()    // Session을 사용하지 않기 때문에 Session 설정을 STATELESS로 설정
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers("/api/**", "/profile").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfiguration(tokenProvider));    // JWTFilter를 addFilterBefore로 등록했던 JWTSecurityConfig 클래스 적용
    }
}