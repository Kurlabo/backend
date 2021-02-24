//package com.kurlabo.backend.service;
//
//import com.kurlabo.backend.model.Member;
//import com.kurlabo.backend.repository.MemberRepository;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//@Component("userDetailsService")
//public class CustomUserDetailsService implements UserDetailsService {
//
//    private final MemberRepository memberRepository;
//
//    public static Long memberId;
//
//    public CustomUserDetailsService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String uid) throws UsernameNotFoundException {
//
//        Member member = memberRepository.findByUid(uid)
//                .orElseThrow(() -> new UsernameNotFoundException(uid + " -> 데이터베이스에서 찾을 수 없습니다."));
//
//        memberId = member.getId();
//
//        return createUser(member.getUid(),member);
//    }
//
//    private User createUser(String uid, Member member) {
//        if(member.isDeleted()) {
//            throw new RuntimeException(uid + " -> 탈퇴한 회원입니다.");
//        }
//        List<GrantedAuthority> grantedAuthorities = Stream.of(member.getRole())
//                .map(authority -> new SimpleGrantedAuthority(authority.getRoleName()))
//                .collect(Collectors.toList());
//
//        return new User(member.getUid(),
//                member.getPassword(),
//                grantedAuthorities);
//    }
//}
