package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.MemberRole;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .check_term(dto.getCheck_term())
                .check_sns(dto.getCheck_sns())
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
        } else {
            throw new DataNotFoundException("NO RESOURCE");
        }

        // int atIdx = member.getEmail().indexOf("@");
        // sb.append(member.getEmail(), 0, atIdx - 5).append("*******").append(member.getEmail().substring(atIdx));

        return FindPwResponseDto.builder()
                .message("SUCCESS")
                .member_id(member.getId())
                .build();
    }

    @Transactional
    public FindPwChangeResponseDto findPwChange(FindPwChangeDto findPwChangeDto) {
        Member member = memberRepository.findById(findPwChangeDto.getMember_id()).orElseThrow(() -> new DataNotFoundException("Member is not existed."));

        member.setPassword(passwordEncoder.encode(findPwChangeDto.getInsertChangePw()));

        memberRepository.save(member);

        return FindPwChangeResponseDto.builder()
                .message("SUCCESS")
                .build();
    }

    public String checkPhone(CheckPhoneDto dto) {
        if(!memberRepository.findByPhone(dto.getCheckPhone()).isPresent()){
            return "NOT EXISTED PHONE NUMBER";
        }
        return "EXISTED PHONE NUMBER";
    }

    public CheckMemberInfoResponseDto checkMemberInfo(Long id, CheckPwDto dto) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Member is not existed.")
        );

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new DataNotFoundException("Passwords do not match.");
        }

        return CheckMemberInfoResponseDto.builder()
                .message("SUCCESS")
                .build();
    }

    public Member getMemberInfo (Long id) {
        Member member = memberRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Member is not existed.")
        );

//        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
//            throw new DataNotFoundException("Passwords do not match.");
//        }

        return Member.builder()
                .uid(member.getUid())
                .name(member.getName())
                .phone(member.getPhone())
                .email(member.getEmail())
                .gender(member.getGender())
                .date_of_birth(member.getDate_of_birth())
                .build();
    }

    @Transactional
    public void updateMember(Long id, MemberDto dto) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Member is not existed."));

        // 현재 비밀번호 기입 && 변경 비밀번호 기입
        if(dto.getCheckPassword() != null && dto.getPassword() != null) {
            // 현재 비밀번호 불일치
            if (!passwordEncoder.matches(member.getPassword(), dto.getCheckPassword())) {
                throw new DataNotFoundException("Passwords do not match.");
            }
            member.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        member.setName(dto.getName());
        member.setEmail(dto.getEmail());
        member.setPhone(dto.getPhone());
        member.setDate_of_birth(dto.getDate_of_birth());
        member.setGender(dto.getGender());

        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Member is not existed."));

        member.setDeleted(true);

        memberRepository.delete(member);
    }


}