package com.kurlabo.backend.repository;

import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long > {

//    @Transactional
//    @Modifying
//    @Query("update Review r set r.help = r.help + 1 where r.review_id = :review_id")
//    int updateHelpCnt(@Param("review_id") Long review_id);
//    // int/Integer 반환만 가능
//    // Modifying는 좋은 방법이 아님 -> 대신 select 하기 전에 데이터베이스에 반영을 해주는 게 좋음

//    @Transactional
//    @Modifying
//    @Query("update Review r set r.cnt = r.cnt + 1 where r.review_id = :review_id")
//    int updateViewCnt(@Param("review_id") Long review_id);

    List<Review> findAllByProduct(Product product);

    List<Review> findByMember(Member member, Pageable pageable);

    @Query("select r from Review r where r.member = :member and r.product = :product")
    List<Review> findByMemberAndProductId(@Param("member") Member member, @Param("product") Product product);
}