package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {
}
