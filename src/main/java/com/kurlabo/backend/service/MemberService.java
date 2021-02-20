package com.kurlabo.backend.service;

import com.kurlabo.backend.config.security.JwtTokenProvider;
import com.kurlabo.backend.dto.MemberDto;
import com.kurlabo.backend.exception.CUserNotFoundException;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Member member = memberRepository.findById(Long.valueOf(id)).orElseThrow(CUserNotFoundException::new);
        return new User(member.getEmail(), member.getPassword(), authorities(member.getRole()));
    }

    private Collection<? extends GrantedAuthority> authorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }

    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Member findByUid(String uid) {
        return memberRepository.findByUid(uid).orElseThrow(ResourceNotFoundException::new);
    }
}
