package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Main_src;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MainSrcRepository extends JpaRepository<Main_src, Long> {
    @Query("select m.setbyul_img from Main_src m where m.id = 1")
    String findSetbyulImg();
}
