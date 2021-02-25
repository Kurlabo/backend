package com.kurlabo.backend.controller;

import com.kurlabo.backend.command.MemberDetailsCommand;
import com.kurlabo.backend.command.MemberRegistrationCommand;
import com.kurlabo.backend.command.MemberUpdateCommand;
import com.kurlabo.backend.dto.member.GetMemberRequestDto;
import com.kurlabo.backend.dto.member.GetMemberResponseDto;
import com.kurlabo.backend.dto.member.LoginRequestDto;
import com.kurlabo.backend.dto.member.UpdateMemberResponseDto;
import com.kurlabo.backend.exception.CommandValidationFailedException;
import com.kurlabo.backend.exception.UserNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//@CrossOrigin
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member")
public class MemberController {

    private final MemberService memberService;

    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody MemberRegistrationCommand command,
                                    Errors errors) {

        if (errors.hasFieldErrors()) {
            throw new CommandValidationFailedException(errors);
        }

        String token = memberService.signUp(command);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(token);
    }

//    @GetMapping(value = "/myinfo")
//    public GetMemberResponseDto getMember (MemberDetailsCommand command, Authentication authentication) {
//
//        log.debug("authenticatioin >> {}", authentication);
//
//        String uid = authentication.getName();
//
//        Member getMember = memberService.getMemberDetails(command, uid);
//        String token = memberService.getAccessToken(getMember);
//
//
//        return GetMemberResponseDto.builder()
//                .accessToken(token)
//                .build();
//    }


    @PutMapping(value = "/myinfo")
    public UpdateMemberResponseDto updateMember(@Valid @RequestBody MemberUpdateCommand command,
                                                Authentication authentication) {

        log.debug("authentication >> {}", authentication);

        String uid = authentication.getName();

        if (!memberService.checkMemberExistsByUID(uid)) {
            throw new UserNotFoundException("존재하지 않는 회원입니다: " + uid);
        }

        Member updatedMember = memberService.updateUser(command, uid);
        String token = memberService.getAccessToken(updatedMember);

        return UpdateMemberResponseDto.builder()
                .accessToken(token)
                .build();
    }
// todo for developing
//
//    @DeleteMapping(value = "/myinfo")
//    public ResponseEntity<Boolean> deleteMember (DeleteMemberRequestDto deleteMemberRequestDto, Authentication authentication) {
//
//        String uid = authentication.getName();
//
//        if (!memberRepository.existsByUid(uid)) {
//            throw new UserNotFoundException("이미 탈퇴한 회원입니다. {" + uid + "}");
//        }
//
//
//        Member deletedMember = memberService.deleteUser(deleteMemberRequestDto);
//
//        return DeleteMemberResponseDto.builder()
//                .accessToken(token)
//                .build();
//
//    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        return memberService.login(loginRequestDto);
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    @GetMapping("/logout")
//    public ResponseEntity<?> logout(LogoutRequestDto logoutRequestDto) {
//        return memberService.logout(logoutRequestDto);
//    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        binder.registerCustomEditor(String.class, stringTrimmerEditor);
    }
}


