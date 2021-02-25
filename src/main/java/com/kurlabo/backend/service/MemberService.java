package com.kurlabo.backend.service;

import com.kurlabo.backend.command.MemberDetailsCommand;
import com.kurlabo.backend.command.MemberRegistrationCommand;
import com.kurlabo.backend.command.MemberUpdateCommand;
import com.kurlabo.backend.config.security.JwtTokenProvider;
import com.kurlabo.backend.dto.member.*;
import com.kurlabo.backend.exception.MemberAlreadyExistsException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.exception.UserNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.kurlabo.backend.service.MemberSecurityService.DEFAULT_USER_ROLE;

@Slf4j
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

    public String signUp(MemberRegistrationCommand command) {
        if (memberRepository.existsByUid(command.getUid())) {
            throw new MemberAlreadyExistsException("이미 존재하는 아이디입니다: " + command.getUid());
        }

        // creates new member from DTO
        Member member = command.toMember(passwordEncoder);

        // persist then returns the persisted one
        member = memberRepository.save(member);

        // creates access token
        return jwtTokenProvider.createAccessToken(member);
    }

//    public ResponseEntity<?> getMemberDetails(MemberDetailsCommand command) {
//
//        jwtTokenProvider
//        command.getMemberDetails();
//        return
//    }


    public Member updateUser(MemberUpdateCommand command, String uid) {

        Member member = memberRepository
                .findByUid(uid)
                .orElseThrow(() -> new UserNotFoundException("사용자가 존재하지 않습니다: " + uid));

        if (command.getPassword() != null && !command.getPassword().isEmpty()) {
            command.setPassword(encodeUserPassword(command.getPassword()));
        }

        command.updateMember(member);

        return memberRepository.save(member);
    }

    public String encodeUserPassword(String password) {
        return passwordEncoder.encode(password);
    }

    public ResponseEntity<?> login(LoginRequestDto loginRequestDto) {

        log.debug("{}", loginRequestDto);

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
                .createAccessToken(member.getUid(), DEFAULT_USER_ROLE);

        Map<String, Object> responseBody = new HashMap<>();

        responseBody.put("accessToken", accessToken);
        responseBody.put("message", "로그인 성공");

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(responseBody);
    }

    public boolean checkMemberExistsByUID(String uid) {
        return memberRepository.existsByUid(uid);
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
        return jwtTokenProvider.createAccessToken(String.valueOf(updatedMember.getUid()), role);
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




}

