package com.kurlabo.backend.controller.prdtcontroller;

import com.kurlabo.backend.dto.MemberDto;
import com.kurlabo.backend.dto.testdto.MemberTestDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/member")
public class MemberController {

    private MemberService memberService;


    @PostMapping("/join")
    public ResponseEntity<MemberDto> join (@RequestBody MemberDto memberDto){

        Member member = memberService.join(memberDto);

        return ResponseEntity.ok(mapperForMemberDto(member));
    }


    @GetMapping("/{member_id}")
    public ResponseEntity<MemberDto> getMember(@PathVariable(name="member_id") Long id) {

        Member member = memberService.getMember(id);


        return ResponseEntity.ok(mapperForMemberDto(member));
    }

//    @PutMapping("/{member_id}")
//    public ResponseEntity<MemberDto> editMember(@PathVariable(name="member_id") Long id,
//                                                @RequestBody MemberDto memberDto) {
//
//        Member member = memberService.editMember(member_id, memberDto);
//
//        return ResponseEntity.ok(mapperForMemberDto(member));
//        }


    private MemberDto mapperForMemberDto(Member member) {
        return MemberDto.builder()
                .uid(member.getUid())
                .password(member.getPassword())
                .name(member.getName())
                .email(member.getEmail())
                .phone(member.getPhone())
                .gender(member.getGender())
                .date_of_birth(member.getDate_of_birth())
                .build();
    }


}
