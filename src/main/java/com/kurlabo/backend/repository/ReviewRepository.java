package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long > {

    Page<Review> findAllByProduct(Product product, Pageable pageable);

    List<Review> findByMember(Member member, Pageable pageable);

    // @Query("select r from Review r where r.member = :member and r.product = :product")
    List<Review> findByMemberAndProduct(Member member, Product product);
}