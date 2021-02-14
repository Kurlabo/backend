package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMember(Member member);

}
