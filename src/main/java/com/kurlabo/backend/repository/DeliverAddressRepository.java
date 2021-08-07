package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DeliverAddressRepository extends JpaRepository<Deliver_Address, Long> {

    List<Deliver_Address> findByMember(Member member);

    @Query("select da from Deliver_Address da where da.member = :member and da.is_main = :isMain")
    Optional<Deliver_Address> findByMemberAndIs_main(Member member, int isMain);

}
