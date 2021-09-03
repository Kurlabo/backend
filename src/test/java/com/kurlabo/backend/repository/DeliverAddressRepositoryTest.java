package com.kurlabo.backend.repository;

import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class DeliverAddressRepositoryTest {

    @Autowired
    private DeliverAddressRepository deliverAddressRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void findByMember() {
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));

        List<Deliver_Address> deliverAddressList = deliverAddressRepository.findByMember(member);

        assertThat(deliverAddressList.get(0).getDeliver_address()).isEqualTo("서울시 강동구 명일동 11");
        assertThat(deliverAddressList.get(0).getDeliver_detail_address()).isEqualTo("115-1304");
        assertThat(deliverAddressList.get(0).getIs_main()).isEqualTo(1L);
        assertThat(deliverAddressList.get(0).getReciever()).isEqualTo("");
        assertThat(deliverAddressList.get(0).getReciever_phone()).isEqualTo("");
        assertThat(deliverAddressList.get(0).getChecked()).isEqualTo(0);

        assertThat(deliverAddressList.get(2).getDeliver_address()).isEqualTo("서울시 강동구 고덕동 392");
        assertThat(deliverAddressList.get(2).getDeliver_detail_address()).isEqualTo("101-302");
        assertThat(deliverAddressList.get(2).getIs_main()).isEqualTo(0);
        assertThat(deliverAddressList.get(2).getReciever()).isEqualTo("임노아");
        assertThat(deliverAddressList.get(2).getReciever_phone()).isEqualTo("01082825959");
        assertThat(deliverAddressList.get(2).getChecked()).isEqualTo(1L);


        assertThat(deliverAddressList.get(0).getMember()).isEqualTo(member);
        assertThat(deliverAddressList.get(1).getMember()).isEqualTo(member);
        assertThat(deliverAddressList.get(2).getMember()).isEqualTo(member);
    }

    @Test
    void findByIdAndMember() {
    }

    @Test
    void findByMemberAndIs_main() {
    }

    @Test
    void findByMemberAndChecked() {
    }
}