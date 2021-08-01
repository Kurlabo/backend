package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.db.Slide_img;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SlideImgRepository extends JpaRepository<Slide_img, Long> {
    @Query("select s.url from Slide_img s")
    List<String> findAllUrl();
}
