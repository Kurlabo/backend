package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliverAddressRepository extends JpaRepository<Deliver_Address, Long> {

    List<Deliver_Address> findByMember(Member member);

}
