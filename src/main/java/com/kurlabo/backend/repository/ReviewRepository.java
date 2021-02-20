package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long > {

    Optional<Review> findByMemberAndReview_id(Member member, Long review_id);

    @Query("update Review r set r.help = r.help + 1 where r.review_id = :review_id")
    Long updateHelpCnt(@Param("review_id")Long review_id);

    Page<Review> findAllByProduct(Product product, Pageable pageable);

    List<Review> findByMember(Member member);
}
