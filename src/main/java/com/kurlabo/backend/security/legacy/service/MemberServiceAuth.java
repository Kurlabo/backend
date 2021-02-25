//package com.kurlabo.backend.security.service;
//
//import com.kurlabo.backend.dto.MemberDto;
//import com.kurlabo.backend.exception.CUserNotFoundException;
//import com.kurlabo.backend.exception.DataNotFoundException;
//import com.kurlabo.backend.exception.ResourceNotFoundException;
//import com.kurlabo.backend.model.Member;
//import com.kurlabo.backend.repository.MemberRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Collection;
//import java.util.Collections;
//@Service
//@RequiredArgsConstructor
//public class MemberServiceAuth {
//
//    private final MemberRepository memberRepository;
//
//    @Transactional
//    public Member join(MemberDto memberDto) {
//
//        Member member = Member.builder()
//                .uid(memberDto.getUid())
//                .password(memberDto.getPassword())
//                .name(memberDto.getName())
//                .email(memberDto.getEmail())
//                .phone(memberDto.getPhone())
//                .gender(memberDto.getGender())
//                .date_of_birth(memberDto.getDate_of_birth())
//                .build();
//
//        return memberRepository.save(member);
//    }
//
//    @Transactional
//    public Member getMember(long id) {
//        return memberRepository
//                .findById(id)
//                .orElseThrow(()-> new DataNotFoundException("조회하는 회원이 존재하지 않습니다."));
//    }
//
//    @Transactional
//    public Member updateMember(Long id, MemberDto memberDto) {
//
//        Member member = memberRepository
//                .findById(id)
//                .orElseThrow(() -> new DataNotFoundException("수정할 멤버가 존재하지 않습니다."));
//
//        //todo 수정관련
//
//        return memberRepository.save(member);
//
//    }
//
//    @Transactional
//    public Member deleteMember(Long id) {
//        Member member = memberRepository
//                .findById(id)
//                .orElseThrow(() -> new DataNotFoundException("해당 멤버가 존재하지 않습니다."));
//
//        //todo 삭제 관련
//
//        return memberRepository.save(member);
//    }
//}
