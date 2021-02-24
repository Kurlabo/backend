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

    private String reciever;

    private String reciever_phone;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void update (String reciever, String reciever_phone) {
        this.reciever = reciever;
        this.reciever_phone = reciever_phone;
    }
}