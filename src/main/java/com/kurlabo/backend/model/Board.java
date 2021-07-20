package com.kurlabo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;
    private String title;
    private String writer;
    private LocalDate regdate;
    private int cnt;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;
}
