package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.MemberDto;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member join(MemberDto memberDto) {

        Member member = Member.builder()
                .uid(memberDto.getUid())
                .password(memberDto.getPassword())
                .name(memberDto.getName())
                .email(memberDto.getEmail())
                .phone(memberDto.getPhone())
                .gender(memberDto.getGender())
                .date_of_birth(memberDto.getDate_of_birth())
                .build();

        return memberRepository.save(member);
    }

    @Transactional
    public Member getMember(long id) {
        return memberRepository
                .findById(id)
                .orElseThrow(()-> new DataNotFoundException("조회하는 회원이 존재하지 않습니다."));
    }


    private final MemberRepository memberRepository;

    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }
}
