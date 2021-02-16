package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByMember(Long member);
}
