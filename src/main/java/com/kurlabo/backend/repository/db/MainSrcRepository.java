package com.kurlabo.backend.repository.db;

import com.kurlabo.backend.model.db.Main_src;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainSrcRepository extends JpaRepository<Main_src, Long> {
    @Query("select m.setbyul_img from Main_src m")
    String findAllBySetbyulImg();
}
