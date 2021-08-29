package com.kurlabo.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String title;

    private String content;

    private String writer;

    private LocalDate regDate;

    private int help;

    private int cnt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void increaseCount() {
        this.cnt++;
    }

    public void increaseHelp() {
        this.help++;
    }
}