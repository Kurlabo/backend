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
    public ResponseEntity<?> getBoardList(@PageableDefault(size = 3) Pageable pageable){

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(boardService.getBoardList(pageable));
    }

    // 공지사항 보기
    @GetMapping("/view/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id){

        HttpHeaders hh = new HttpHeaders();                 // 나중에 필터로 리팩토링 해야함
        hh.set("Access-Control-Allow-Origin", "*");

        return ResponseEntity.ok()
                .headers(hh)
                .body(boardService.getBoard(id));
    }
}
