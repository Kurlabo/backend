package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.MessageResponseDto;
import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.InvalidPasswordException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.MemberRole;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeliverAddressRepository deliverAddressRepository;

    @Transactional
    public MessageResponseDto signUp(MemberDto dto){
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

        Deliver_Address deliver_address = Deliver_Address.builder()
                .id(null)
                .deliver_address(dto.getAddress())
                .deliver_detail_address(dto.getDetail_address())
                .is_main(1)
                .reciever("")
                .reciever_phone("")
                .checked(1)
                .member(member)
                .build();

        memberRepository.save(member);
        deliverAddressRepository.save(deliver_address);

        return MessageResponseDto.builder().message("SIGNUP SUCCESS").build();
    }

    @Transactional
    public void updateMember(Long memberId, MemberDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));

        member.update(dto, passwordEncoder);

        memberRepository.save(member);
    }

    @Transactional
    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));

        member.setDeleted(true);

        memberRepository.save(member);
    }

    public MessageResponseDto findAllByUid(CheckUidDto dto){
        if(memberRepository.findAllByUid(dto.getCheckUid()).size() > 0){
            return MessageResponseDto.builder().message("EXISTED UID").build();
        }
        return MessageResponseDto.builder().message("NOT EXISTED UID").build();
    }

    public MessageResponseDto findAllByEmail(CheckEmailDto dto) {
        if(memberRepository.findAllByEmail(dto.getCheckEmail()).size() > 0){
            MessageResponseDto.builder().message("EXISTED EMAIL").build();
        }
        return MessageResponseDto.builder().message("NOT EXISTED EMAIL").build();
    }

    public FindIdResponseDto findByNameAndEmail(FindIdDto findIdDto) {
        Member member = memberRepository.findByNameAndEmail(findIdDto.getName(), findIdDto.getEmail()).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다."));

        return FindIdResponseDto.builder()
                .message("SUCCESS")
                .uid(member.getUid().substring(0, member.getUid().length() - 3) + "***")
                .build();
    }

    public FindPwChangeDto findByNameAndUidAndEmail(FindPwDto findPwDto) {
        Member member = memberRepository.findByNameAndUidAndEmail(findPwDto.getName(), findPwDto.getUid(), findPwDto.getEmail()).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다."));

        return FindPwChangeDto.builder().member_id(member.getId()).build();
    }

    @Transactional
    public MessageResponseDto setPassword(FindPwChangeDto findPwChangeDto) {
        Member member = memberRepository.findById(findPwChangeDto.getMember_id()).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + findPwChangeDto.getMember_id()));

        member.setPassword(passwordEncoder.encode(findPwChangeDto.getInsertChangePw()));

        memberRepository.save(member);

        return MessageResponseDto.builder().message("SUCCESS").build();
    }

    public MessageResponseDto findByPhone(CheckPhoneDto dto) {
        if(!memberRepository.findByPhone(dto.getCheckPhone()).isPresent()){
            throw new DataNotFoundException("NOT EXISTED PHONE NUMBER");
        }
        return MessageResponseDto.builder().message("EXISTED PHONE NUMBER").build();
    }

    public MessageResponseDto checkPassword(Long memberId, CheckPwDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않습니다.");
        }

        return MessageResponseDto.builder().message("SUCCESS").build();
    }

    public MemberDto getMemberInfo (Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));
        return member.toMemberDto();
    }
}