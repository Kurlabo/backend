package com.kurlabo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Deliver_Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliver_address_id")
    private Long id;
    private String deliver_address;
    private int is_main;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}