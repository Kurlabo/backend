package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeliverAddressRepository extends JpaRepository<Deliver_Address, Long> {
    
    List<Deliver_Address> findByMember(Member member);

    Optional<Deliver_Address> findByMemberAndIs_main(Member member, int isMain);
}
