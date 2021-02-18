package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
