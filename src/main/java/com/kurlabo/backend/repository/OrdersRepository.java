package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByMember(Member member);

    List<Orders> findByMemberAndStatus(Member member, String str);

    Optional<Orders> findByStatus(String str);

    @Query("select o from Orders o where o.member = :member and o.checkoutDate >= :chkDate and o.checkoutDate < :now" )
    List<Orders> findByMemberAndCheckoutDate(Member member, LocalDate chkDate, LocalDate now);
}
