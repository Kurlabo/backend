package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.BoardDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/board")
public class BoardController {

    @GetMapping("")
    public BoardDto read () {
        BoardDto boardDto = new BoardDto();

        boardDto.setBoard_id(1L);
        boardDto.setTitle("공지사항 제목");
        boardDto.setWriter("작성자");
        boardDto.setCnt(0);

        return boardDto;
    }

    @GetMapping("/{id}")
    public BoardDto read (@PathVariable(name="id") Long id) {
        BoardDto boardDto = new BoardDto();

        boardDto.setBoard_id(1L);
        boardDto.setTitle("공지사항 제목1");
        boardDto.setWriter("작성자");
        boardDto.setContent("공지사항 내용1");
        boardDto.setCnt(1);

        return boardDto;
    }
}
