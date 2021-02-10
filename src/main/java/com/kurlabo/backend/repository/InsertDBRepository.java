package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsertDBRepository extends JpaRepository<Product, Long> {
}
