package com.kurlabo.backend.service;

import com.kurlabo.backend.exception.UserNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberSecurityService implements UserDetailsService {

    public static final String DEFAULT_USER_ROLE = "USER";

    private final MemberRepository memberRepository;

    private Collection<? extends GrantedAuthority> authorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        Member member = memberRepository.findByUid(userId).orElseThrow(UserNotFoundException::new);
        return new User(member.getUid(), member.getPassword(), authorities(member.getRole()));
    }
}
