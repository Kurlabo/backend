package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Favorite;
import com.kurlabo.backend.model.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

//    Page<Favorite> findByMember(Member member, Pageable pageable);
    List<Favorite> findByMember(Member member, Pageable pageable);

    @Query("select f from Favorite f where f.member = :member and f.products_id = :product_id")
    Favorite findByMemberAndProductId(@Param("member") Member member, @Param("product_id") Long product_id);

}
