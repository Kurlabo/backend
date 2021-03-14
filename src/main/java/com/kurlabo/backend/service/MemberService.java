package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.MemberRole;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeliverAddressService deliverAddressService;

    @Transactional
    public String signUp(MemberDto dto){
        if (checkUid(new CheckUidDto(dto.getUid())).equals("EXISTED UID")){
            return "SIGNUP FAILED(EXISTED UID)";
        }
        else if (checkEmail(new CheckEmailDto(dto.getEmail())).equals("EXISTED EMAIL")){
            return "SIGNUP FAILED(EXISTED EMAIL)";
        }

        Member member = signUpMember(dto);

        Deliver_Address da = deliverAddressService.setDeliverAddress(member, dto.getAddress());

        return "SIGNUP SUCCESS";
    }

    @Transactional
    public Member signUpMember(MemberDto dto){
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
                .role(MemberRole.MEMBER)
                .build();

        return memberRepository.save(member);
    }

    public String checkUid(CheckUidDto dto){
        if(memberRepository.findAllByUid(dto.getCheckUid()).size() > 0){
            return "EXISTED UID";
        }
        return "NOT EXISTED UID";
    }

    public String checkEmail(CheckEmailDto dto) {
        if(memberRepository.findAllByEmail(dto.getCheckEmail()).size() > 0){
            return "EXISTED EMAIL";
        }
        return "NOT EXISTED EMAIL";
    }

    private Collection<? extends GrantedAuthority> authorities(String role) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role));
    }
    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public FindIdResponseDto findId(FindIdDto findIdDto) {
        Optional<Member> memberOptional = memberRepository.findByNameAndEmail(findIdDto.getName(), findIdDto.getEmail());
        Member member;

        if(memberOptional.isPresent()){
            member = memberOptional.get();
        } else {
            throw new DataNotFoundException("NO RESOURCE");
        }

        String responseUid = member.getUid().substring(0, member.getUid().length() - 3) + "***";

        return FindIdResponseDto.builder()
                .message("SUCCESS")
                .uid(responseUid)
                .build();
    }

    public FindPwResponseDto findPw(FindPwDto findPwDto) {
        Optional<Member> optionalMember = memberRepository.findByNameAndUidAndEmail(findPwDto.getName(), findPwDto.getUid(), findPwDto.getEmail());
        // StringBuilder sb = new StringBuilder();
        Member member;

        if(optionalMember.isPresent()){
            member = optionalMember.get();
            member.setPassword(passwordEncoder.encode(findPwDto.getPassword()));
            memberRepository.save(member);
        } else {
            throw new DataNotFoundException("NO RESOURCE");
        }

        // int atIdx = member.getEmail().indexOf("@");
        // sb.append(member.getEmail(), 0, atIdx - 5).append("*******").append(member.getEmail().substring(atIdx));

        return FindPwResponseDto.builder()
                .message("SUCCESS")
                //.email(sb.toString())
                .build();
    }

    // newly added
    public String checkPhone(CheckPhoneDto dto) {
        if(!memberRepository.findByPhone(dto.getCheckPhone()).isPresent()){
            return "NOT EXISTED PHONE NUMBER";
        }
        return "EXISTED PHONE NUMBER";
    }

    public Member getMemberInfo (Long id, MemberDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Member is not existed."));

        // 이름 아이디 번호 이메일 성별 생일 (비밀번호 빼고 다)
        return Member.builder()
                .uid(dto.getUid())
                .name(dto.getName())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .date_of_birth(dto.getDate_of_birth())
                .build();
    }

//    @Transactional
//    public void updateMember(Long id, MemberDto dto) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new DataNotFoundException("Member is not existed."));
//
//        if(dto.getPassword() != null) {
//            member.setPassword(passwordEncoder.encode(dto.getPassword()));
//        }
//
//        member.setName(dto.getName());
//        member.setEmail(dto.getEmail());
//        member.setPhone(dto.getPhone());
//        member.setDate_of_birth(dto.getDate_of_birth());
//        member.setGender(dto.getGender());
//
//        memberRepository.save(member);
//    }
//
//    @Transactional
//    public void deleteMember(Long id) {
//        Member member = memberRepository.findById(id)
//                .orElseThrow(() -> new DataNotFoundException("Member is not existed."));
//
//        member.setDeleted(true);
//
//        memberRepository.delete(member);
//    }
}