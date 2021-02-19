package com.kurlabo.backend.model.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Insta_src {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "insta_src_id")
    private Long id;
    private String landing_url;
    @Column(length = 1000)
    private String thumbnail_img_url;
}
