package com.kurlabo.backend.model;

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
    private Long id;
    private String landing_url;
    @Column(length = 1000)
    private String thumbnail_img_url;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "main_src_id")
    private Main_src main_src;
}
