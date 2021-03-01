package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Orders;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByMember(Member member);

    List<Orders> findAllByStatus(String str);

    @Query("select o from Orders o where o.member = :member and o.checkoutDate >= :chkDate and o.checkoutDate < :now" )
    List<Orders> findByMemberAndCheckoutDate(Member member, LocalDate chkDate, LocalDate now);
}
