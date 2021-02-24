package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface DeliverAddressRepository extends JpaRepository<Deliver_Address, Long> {
    List<Deliver_Address> findByMember(Member member);

//    @Transactional
//    @Modifying
//    @Query("update Deliver_Address d set d.is_main = 0 where d.member = :member")
//    int findByMemberUpdate(Member member);

}
