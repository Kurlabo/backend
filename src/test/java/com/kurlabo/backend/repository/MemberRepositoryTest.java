package com.kurlabo.backend.repository;

import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버 조회")
    @Test
    void findById() {
        Long memberId = 1L;

        Member member = memberRepository.findById(memberId).orElseThrow(ResourceNotFoundException::new);

        assertThat(member.getId()).isEqualTo(memberId);
        assertThat(member.getName()).isEqualTo("임정우1");
    }

    @DisplayName("UID로 멤버 조회")
    @Test
    void findByUid() {
        String memberUid = "testid4";

        Member member = memberRepository.findByUid(memberUid).orElseThrow(ResourceNotFoundException::new);

        assertThat(member.getUid()).isEqualTo(memberUid);
        assertThat(member.getName()).isEqualTo("임정우4");
    }

    @DisplayName("Email로 멤버 조회")
    @Test
    void findAllByEmail() {
        String memberEmail = "limjw01@gmail.com";

        Member member = memberRepository.findAllByEmail(memberEmail).get(0);

        assertThat(member.getEmail()).isEqualTo(memberEmail);
        assertThat(member.getName()).isEqualTo("임정우1");
    }

    @DisplayName("UID로 모든 멤버 조회")
    @Test
    void findAllByUid() {
        String memberUid = "testid3";

        Member member = memberRepository.findAllByUid(memberUid).get(0);

        assertThat(member.getUid()).isEqualTo(memberUid);
        assertThat(member.getName()).isEqualTo("임정우3");
    }

    @DisplayName("이름, Email로 멤버 조회")
    @Test
    void findByNameAndEmail() {
        String memberName = "임정우1";
        String memberEmail = "limjw01@gmail.com";

        Member member = memberRepository.findByNameAndEmail(memberName, memberEmail).orElseThrow(ResourceNotFoundException::new);

        assertThat(member.getName()).isEqualTo(memberName);
        assertThat(member.getEmail()).isEqualTo(memberEmail);
        assertThat(member.getUid()).isEqualTo("testid1");
    }

    @DisplayName("이름, UID, Email로 멤버 조회")
    @Test
    void findByNameAndUidAndEmail() {
        String memberName = "임정우4";
        String memberUid = "testid4";
        String memberEmail = "limjw04@gmail.com";

        Member member = memberRepository.findByNameAndUidAndEmail(memberName, memberUid, memberEmail).orElseThrow(ResourceNotFoundException::new);

        assertThat(member.getName()).isEqualTo(memberName);
        assertThat(member.getUid()).isEqualTo(memberUid);
        assertThat(member.getEmail()).isEqualTo(memberEmail);
    }

    @DisplayName("Phone 번호로 멤버 조회")
    @Test
    void findByPhone() {
        String memberPhone = "01033333333";

        Member member = memberRepository.findByPhone(memberPhone).orElseThrow(ResourceNotFoundException::new);

        assertThat(member.getPhone()).isEqualTo(memberPhone);
        assertThat(member.getName()).isEqualTo("임정우3");
    }
}