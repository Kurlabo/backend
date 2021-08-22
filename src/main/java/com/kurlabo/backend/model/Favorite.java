package com.kurlabo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Favorite {
    @Id
    @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;
    private Long products_id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
