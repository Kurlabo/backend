package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByMember(Member member);
}
