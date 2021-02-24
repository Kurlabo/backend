package com.kurlabo.backend.service;

import com.kurlabo.backend.config.security.JwtTokenProvider;
import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.exception.MemberAlreadyExistsException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.exception.UserNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.kurlabo.backend.service.MemberSecurityService.DEFAULT_USER_ROLE;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
//    private final MemberSecurityService securityService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public Member findById(Long id){
        return memberRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public Member findByUid(String uid) {
        return memberRepository.findByUid(uid).orElseThrow(ResourceNotFoundException::new);
    }

    public String signUp(SignupRequestDto dto) {

        if(dto.toMember(jwtTokenProvider.validateToken()))

        if (memberRepository.existsByUid(dto.getUid())) {
            throw new MemberAlreadyExistsException("member with uid {" + dto.getUid() + "} already exists");
        }

        // set default grade and role for constant values.
        dto.setDefaultRoleAndGrade();

        // creates new member from DTO
        Member member = dto.toMember(passwordEncoder);

        // persist then returns the persisted one
        member = memberRepository.save(member);

        // creates access token
        return jwtTokenProvider.createAccessToken(member);
    }

    public Member updateUser(UpdateMemberRequestDto updateMemberRequestDto, String uid) {

        Member member = memberRepository
                .findByUid(uid)
                .orElseThrow(() -> new UserNotFoundException("User with uid {" + uid + "} not found"));

        if (updateMemberRequestDto.getEmail() != null) {
            member.setEmail(updateMemberRequestDto.getEmail());
        } else if (updateMemberRequestDto.getName() != null) {
            member.setName(updateMemberRequestDto.getName());
        } else if (updateMemberRequestDto.getPhone() != null) {
            member.setPhone(updateMemberRequestDto.getPhone());
        } else if (updateMemberRequestDto.getGender() != null) {
            member.setGender(updateMemberRequestDto.getGender());
        } else if (updateMemberRequestDto.getDate_of_birth() != null) {
            member.setDate_of_birth((updateMemberRequestDto.getDate_of_birth()));
        } else if (updateMemberRequestDto.getPassword() != null) {
            member.setPassword(encodeUserPassword(updateMemberRequestDto.getPassword()));
        }

        return memberRepository.save(member);
    }

    public String encodeUserPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {

        Optional<Member> optMember = this.memberRepository.findByUid(loginRequestDto.getUid());

        if (!optMember.isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(getFailedResponseDto("사용자 아이디가 존재하지 않습니다"));
        }

        Member member = optMember.get();

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), member.getPassword())) {
            return ResponseEntity
                    .badRequest()
                    .body(getFailedResponseDto("비밀번호가 일치하지 않습니다"));
        }

        String accessToken = jwtTokenProvider
                .createAccessToken(String.valueOf(member.getId()), DEFAULT_USER_ROLE);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("accessToken", accessToken);
        responseBody.put("message", "로그인 성공");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    //////todo after update function.
//
//    public ResponseEntity<?> logout(LogoutRequestDto logoutRequestDto) {
//
//        Optional<Member> loginMember = this.memberRepository.findByUid(logoutRequestDto.getAccessToken());
//
//        if (!loginMember.isPresent()) {
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(getFailedResponseDto("사용자 아이디가 존재하지 않습니다"));
//        }
//
//        Member member = loginMember.get();
//
//        if (!passwordEncoder.matches(logoutRequestDto.getAccessToken(), member.getPassword())){
//            return ResponseEntity
//                    .badRequest()
//                    .body(getFailedResponseDto("비밀번호가 일치하지 않습니다"));
//        }
//
//        String accessToken = jwtTokenProvider
//                .createAccessToken(String.valueOf(member.getId()), DEFAULT_USER_ROLE);
//
//        Map<String, Object> responseBody = new HashMap<>();
//        responseBody.put("accessToken", accessToken);
//        responseBody.put("message", "로그인 아웃");
//
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(responseBody);
//    }


    public String getAccessToken(Member updatedMember, String role) {
        return jwtTokenProvider.createAccessToken(String.valueOf(updatedMember.getId()), role);
    }

    public String getAccessToken(Member updatedMember) {
        return getAccessToken(updatedMember, DEFAULT_USER_ROLE);
    }

    private LoginResponseDto getFailedResponseDto(String message) {
        return LoginResponseDto.builder()
                .message(message)
                .accessToken(null)
                .build();
    }

//    public  Member getMember(GetMemberRequestDto getMemberRequestDto) {
//
//
//    }
}

