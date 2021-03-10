package com.kurlabo.backend.security.jwt;

import com.kurlabo.backend.exception.InvalidTokenException;
import com.kurlabo.backend.security.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.security.Key;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TokenProvider implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private static final String AUTHORITIES_KEY = "auth";

    private final RedisTemplate<String, String> redisTemplate;

    private @Value("${jwt.secret}") String secret;

    private @Value("${jwt.token-validity-in-seconds}") long tokenValidityInSeconds;

    private Key key;

    // Bean이 생성이 되고 의존성 주입을 받은 후에 secret 값을 Base64 Decode해서 key변수에 할당
    @Override
    public void afterPropertiesSet() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }


    // Authentication 객체의 권한 정보를 이용해서 토큰을 생성하는 createToken 메소드
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        // Token의 expire time 설정
        Date validity = Date.from(ZonedDateTime.now().plusSeconds(tokenValidityInSeconds).toInstant());

        // JWT 토큰을 생성해서 리턴
        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .claim("id", CustomUserDetailsService.id)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }


    public Long parseTokenToGetMemberId(String token) {
        if (token.startsWith("B")) {
            token = token.substring(JwtFilter.HEADER_PREFIX.length());
        }
        Claims claims = getClaimsFromToken(token);
        String memberId = String.valueOf(claims.get("id"));
        return  Long.parseLong(memberId);
    }

    // Token에 담겨잇는 정보를 이용해 Authentication 객체를 리턴하는 메소드
    public Authentication getAuthentication(String token) {

        // 토큰으로 클레임을 만듬
        Claims claims = getClaimsFromToken(token);

        // 클레임에서 권한 정보들을 받아옴
        Collection<? extends  GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 권한정보들을 이용해서 유저 객체를 만듬
        User principal = new User(claims.getSubject(), "", authorities);

        // Authentication 객체를 리턴
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // 토큰의 유효성 검증을 수행하는 메소드
    public boolean validateToken(String token) {
        // 토큰을 받아 파싱해보고 나오는 Exception들을 캐치 문제가 있으면 false, 정상이면 true
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            checkTokenBlackList(JwtFilter.HEADER_PREFIX + token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        } catch (InvalidTokenException e) {
            logger.info("로그아웃된 토큰입니다");
        }
        return false;
    }

    private void checkTokenBlackList(String jwt) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        if (StringUtils.hasText(valueOperations.get(jwt))) {
            throw new InvalidTokenException("유효하지 않은 토큰으로 접근했습니다.");
        }
    }
}
