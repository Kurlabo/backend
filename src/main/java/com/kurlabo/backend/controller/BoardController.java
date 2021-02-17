package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.BoardTestDto;
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
    public BoardTestDto read () {
        BoardTestDto boardTestDto = new BoardTestDto();

        boardTestDto.setBoard_id(1L);
        boardTestDto.setTitle("공지사항 제목");
        boardTestDto.setWriter("작성자");
        boardTestDto.setCnt(0);

        return boardTestDto;
    }

    @GetMapping("/{id}")
    public BoardTestDto read (@PathVariable(name="id") Long id) {
        BoardTestDto boardTestDto = new BoardTestDto();

        boardTestDto.setBoard_id(1L);
        boardTestDto.setTitle("공지사항 제목1");
        boardTestDto.setWriter("작성자");
        boardTestDto.setContent("공지사항 내용1");
        boardTestDto.setCnt(1);

        return boardTestDto;
    }
}
