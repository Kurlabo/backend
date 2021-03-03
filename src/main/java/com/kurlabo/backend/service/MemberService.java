package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.member.CheckEmailDto;
import com.kurlabo.backend.dto.member.CheckUidDto;
import com.kurlabo.backend.dto.member.MemberDto;
import com.kurlabo.backend.exception.CUserNotFoundException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.MemberRole;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String signUp(MemberDto dto){

        System.out.println("dto >>>>>>>>>>>>>>>>> "  + dto);

        Member member = Member.builder()
                .uid(dto.getUid())
                .password(passwordEncoder.encode(dto.getPassword()))
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .date_of_birth(dto.getDate_of_birth())
                .grade("일반")
                .total_cost(0)
                .role(MemberRole.Member)
                .build();

        memberRepository.save(member);

        return "SIGNUP SUCCESS";
    }

    public String checkUid(CheckUidDto dto){
        if(memberRepository.findByUid(dto.getCheckUid()) == null){
            return "NOT EXISTED UID";
        }
        return "EXISTED UID";
    }

    public String checkEmail(CheckEmailDto dto) {
        if(!memberRepository.findByEmail(dto.getCheckEmail()).isPresent()){
            return "NOT EXISTED EMAIL";
        }
        return "EXISTED EMAIL";
    }

    private Collection<? extends GrantedAuthority> authorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }
    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }


}