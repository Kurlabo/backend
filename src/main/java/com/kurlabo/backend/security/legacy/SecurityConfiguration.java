//package com.kurlabo.backend.security;
//
//import com.kurlabo.backend.security.jwt.JwtAccessDeniedHandler;
//import com.kurlabo.backend.security.jwt.JwtAuthenticationEntryPoint;
//import com.kurlabo.backend.security.jwt.JwtSecurityConfiguration;
//import com.kurlabo.backend.security.jwt.JwtTokenProvider;
//import org.springframework.context.annotation.Bean;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//@EnableWebSecurity
//public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
//
//    private final JwtTokenProvider jwtTokenProvider;
//
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
//
//    public SecurityConfiguration(
//            JwtTokenProvider jwtTokenProvider,
//            JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
//            JwtAccessDeniedHandler jwtAccessDeniedHandler
//    ) {
//        this.jwtTokenProvider = jwtTokenProvider;
//        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
//        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
//    }
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//
//                .exceptionHandling()
//                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .accessDeniedHandler(jwtAccessDeniedHandler)
//
//                .and()
//                .headers()
//                .frameOptions()
//                .sameOrigin()
//
//                .and()
//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//
//                .and()
//                .authorizeRequests()
//                .antMatchers("/api/**").permitAll()
//                .antMatchers("/").permitAll()
//                .anyRequest().authenticated()
//
//                .and()
//                .apply(new JwtSecurityConfiguration(jwtTokenProvider));
//    }
//
//}
