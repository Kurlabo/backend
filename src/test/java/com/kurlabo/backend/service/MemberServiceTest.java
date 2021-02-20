//package com.kurlabo.backend.service;
//
//import com.kurlabo.backend.dto.testdto.MemberTestDto;
//import com.kurlabo.backend.model.Member;
//import com.kurlabo.backend.repository.MemberRepository;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Optional;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class MemberServiceTest {
//
//    @InjectMocks
//    private MemberService memberService;
//
//    @Mock
//    private MemberRepository memberRepository;
//
//    @Test
//    public void joinTest() {
//        Member member = Member.builder()
//                .uid("Tester01")
//                .password("test")
//                .name("Tester")
//                .email("test01@fastcampus.com")
//                .phone("010-1111-2222")
//                .address("서울시 성동구 성수길 77")
//                .gender("남자")
//                .date_of_birth("1991-03-01")
//                .build();
//
//        when(memberRepository.save(member))
//                .thenReturn(member);
//
//        MemberTestDto memberDto = MemberTestDto.builder()
//                .uid("Tester01")
//                .password("test")
//                .name("Tester")
//                .email("test01@fastcampus.com")
//                .phone("010-1111-2222")
//                .address("서울시 성동구 성수길 77")
//                .gender("남자")
//                .date_of_birth("1991-03-01")
//                .build();
//
//        Member result = memberService.join(memberDto);
//
//        assertThat(result.getEmail()).isEqualTo("test01@fastcampus.com");
//    }
//
//    @Test
//    public void getMemberTest() {
//        Member member = Member.builder()
//                .id(1L)
//                .email("member@test.com")
//                .build();
//
//        when(memberRepository.findById(1L))
//                .thenReturn(Optional.ofNullable(member));
//
//        Member result = memberService.getMember(1L);
//
//        assertThat(result.getEmail()).isEqualTo("member@test.com");
//    }
//}
