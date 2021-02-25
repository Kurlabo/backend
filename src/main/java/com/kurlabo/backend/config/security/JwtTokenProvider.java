package com.kurlabo.backend.config.security;

import com.kurlabo.backend.model.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("spring.jwt.secret")
    private String secretKey;

    // >> tokenValidMillis?
    private long tokenValidMilisecond = 86400000;

    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }


    /*
    creates access token from userPK and role based on request FROM controller.
    THIS implies that the userPK is indeed a PK of RDBMS that preserves a user.
    MEANING... we assume that the ID is indeed a PK in such case.

    the role is fixed to 'USER' such that we as a coders would NOT have to think of which role that it takes.
     */
    public String createAccessToken(String userId, String role) {

        Claims claims = Jwts.claims().setSubject(userId);

        claims.put("roles", role);

        // readable
        Date date = Date.from(Instant.now());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + tokenValidMilisecond))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String createAccessToken(Member member) {
        return createAccessToken(member.getUid(), member.getRole());
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.parseUserId(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String parseUserId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String jwtToken) {
        log.info("secretKey set as {}!", secretKey);
        try {
            log.debug("resolving token {}", jwtToken);
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);

            log.debug("claims found as ... {}", claims);
            return !claims.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            log.error("failed to validate token {} >> {}", jwtToken, e.getMessage());
            return false;
        }
    }

}
