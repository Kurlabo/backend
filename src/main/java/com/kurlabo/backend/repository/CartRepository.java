package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByMember(Member member);

    @Query("select c from Cart c where c.member = :member and c.product_id = :product_id")
    Cart findByMemberAndProduct_id(Member member, Long product_id);

}
