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
    private Long review_id;

    private String title;

    private String content;

    private String writer;

    private LocalDate regdate;

    private Long help;

    private Long cnt;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void increaseCount() {
        if (cnt == null) {
            cnt = 0L;
        }

        this.cnt++;
    }

    public void increaseHelp() {
        if (help == null) {
            help = 0L;
        }

        this.help++;
    }
}