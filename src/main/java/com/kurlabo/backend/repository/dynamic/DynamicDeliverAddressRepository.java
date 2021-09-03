package com.kurlabo.backend.repository.dynamic;

import com.kurlabo.backend.dto.mypage.DeliverAddressDto;
import com.kurlabo.backend.dto.mypage.QDeliverAddressDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.QDeliver_Address;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DynamicDeliverAddressRepository {

    private final JPAQueryFactory queryFactory;

    QDeliver_Address qDeliverAddress = QDeliver_Address.deliver_Address;

    public List<DeliverAddressDto> findByMember(Member member){
        return queryFactory
                .select(new QDeliverAddressDto(qDeliverAddress.id, qDeliverAddress.deliver_address, qDeliverAddress.deliver_detail_address,
                        qDeliverAddress.is_main, qDeliverAddress.reciever, qDeliverAddress.reciever_phone, qDeliverAddress.checked))
                .from(qDeliverAddress)
                .where(qDeliverAddress.member.eq(member))
                .fetch();
    }
}
