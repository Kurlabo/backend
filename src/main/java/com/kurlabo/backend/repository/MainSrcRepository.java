package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.db.Main_src;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainSrcRepository extends JpaRepository<Main_src, Long> {
}