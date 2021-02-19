package com.kurlabo.backend.repository.db;

import com.kurlabo.backend.model.db.Insta_src;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InstaSrcRepository extends JpaRepository<Insta_src, Long> {
    @Query("select i.landing_url from Insta_src i")
    List<String> findAllByLandingUrl();

    @Query("select i.thumbnail_img_url from Insta_src i")
    List<String> findAllByThumbnail_img_url();
}
