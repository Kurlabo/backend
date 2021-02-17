package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.BoardTestDto;
import com.kurlabo.backend.model.Board;
import com.kurlabo.backend.repository.BoardRepository;
import com.kurlabo.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/board")
public class BoardController {

    private final BoardService boardService;

    // 공지사항 리스트
    @GetMapping("/list")
    public Page<Board> getBoardList(@PageableDefault(size = 3) Pageable pageable){
        return boardService.getBoardList(pageable);
    }

    // 공지사항 보기
    @GetMapping("/view/{id}")
    public Board getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }
}
