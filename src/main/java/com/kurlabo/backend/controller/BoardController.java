package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.BoardTestDto;
import com.kurlabo.backend.model.Board;
import com.kurlabo.backend.repository.BoardRepository;
import com.kurlabo.backend.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/board")
public class BoardController {

    private final BoardService boardService;

    // 공지사항 리스트
    @GetMapping("/list")
    public ResponseEntity<?> getBoardList(@PageableDefault(size = 3) Pageable pageable){

        return ResponseEntity.ok(boardService.getBoardList(pageable));
    }

    // 공지사항 보기
    @GetMapping("/view/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id){

        return ResponseEntity.ok(boardService.getBoard(id));
    }
}
