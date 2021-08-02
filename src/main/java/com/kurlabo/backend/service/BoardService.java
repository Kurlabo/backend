package com.kurlabo.backend.service;

import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Board;
import com.kurlabo.backend.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public Page<Board> getBoardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Board getBoard(Long boardId){
        return boardRepository.findById(boardId).orElseThrow(() -> new DataNotFoundException("해당 공지사항이 없습니다. boardId = " + boardId));
    }
}