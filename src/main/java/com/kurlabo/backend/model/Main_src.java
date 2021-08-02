package com.kurlabo.backend.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Main_src {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "main_src_id")
    private Long id;
    private String setbyul_img;
}
