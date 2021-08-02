package com.kurlabo.backend.model;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Slide_img {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "slide_img_id")
    private Long id;
    private String url;
}
