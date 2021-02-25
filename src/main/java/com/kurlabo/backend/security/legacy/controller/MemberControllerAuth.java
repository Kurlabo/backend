//package com.kurlabo.backend.security.controller;
//
//import com.kurlabo.backend.dto.MemberDto;
//import com.kurlabo.backend.model.Member;
//import com.kurlabo.backend.security.service.MemberServiceAuth;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(value="/api/member")
//public class MemberControllerAuth {
//
//    private MemberServiceAuth memberServiceAuth;
//
//
//    @PostMapping("/join")
//    public ResponseEntity<MemberDto> join (@RequestBody MemberDto memberDto){
//
//        Member member = memberServiceAuth.join(memberDto);
//
//        return ResponseEntity.ok(mapperForMemberDto(member));
//    }
//
//
//    @GetMapping("/{member_id}")
//    public ResponseEntity<MemberDto> getMember(@PathVariable(name="member_id") Long id) {
//
//        Member member = memberServiceAuth.getMember(id);
//
//
//        return ResponseEntity.ok(mapperForMemberDto(member));
//    }
//
//    @PutMapping("/{member_id}")
//    public ResponseEntity<MemberDto> updateMember(@PathVariable(name="member_id") Long id,
//                                                  @RequestBody MemberDto memberDto) {
//
//        Member member = memberServiceAuth.updateMember(member_id, memberDto);
//
//        return ResponseEntity.ok(mapperForMemberDto(member));
//    }
//
//    @DeleteMapping("/{member_id")
//    public ResponseEntity<MemberDto> deleteMember(@PathVariable(name="member_id") Long id,
//                                                  HttpServletRequest request) {
//        Member member = memberServiceAuth.deleteMember(id);
//
//        return ResponseEntity.ok(member.isDeleted());
//    }
//
//
//    private MemberDto mapperForMemberDto(Member member) {
//        return MemberDto.builder()
//                .uid(member.getUid())
//                .password(member.getPassword())
//                .name(member.getName())
//                .email(member.getEmail())
//                .phone(member.getPhone())
//                .gender(member.getGender())
//                .date_of_birth(member.getDate_of_birth())
//                .build();
//    }
//
//
//}
