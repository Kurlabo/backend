package com.kurlabo.backend.repository;

import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Board;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BoardRepositoryTest {

    @Autowired
    private BoardRepository boardRepository;

    @DisplayName("공지사항 조회")
    @Test
    void findById(){
        Long boardId = 3L;

        Board board = boardRepository.findById(boardId).orElseThrow(() -> new DataNotFoundException("해당 공지사항을 찾을 수 없습니다. Id = " + boardId));

        assertThat(board.getId()).isEqualTo(boardId);
        assertThat(board.getTitle()).isEqualTo("에코박스 도입에 따른 한시적 포장 방법 변경 공지(종료 시점 확정)");
        assertThat(board.getWriter()).isEqualTo("MarketKurly");
        assertThat(board.getContent()).contains("에코박스의 종료");
        assertThat(board.getCnt()).isEqualTo(2828);
        assertThat(board.getRegdate()).isEqualTo(LocalDate.of(2017, 4, 6));
    }

    @DisplayName("공지사항 리스트 조회")
    @Test
    void findAll(){
        Pageable pageable = PageRequest.of(1,3);
        Board board = boardRepository.findAll(pageable).toList().get(0);

        assertThat(board.getId()).isEqualTo(4L);
        assertThat(board.getTitle()).isEqualTo("주문취소마감 시간 변경 공지");
        assertThat(board.getWriter()).isEqualTo("MarketKurly");
        assertThat(board.getContent()).contains("단순변심으로");
        assertThat(board.getCnt()).isEqualTo(7190);
        assertThat(board.getRegdate()).isEqualTo(LocalDate.of(2016, 3, 22));
    }

}