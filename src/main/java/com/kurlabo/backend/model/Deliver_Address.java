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
public class Deliver_Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deliver_address_id")
    private Long id;

    private String deliver_address;

    private String deliver_detail_address;

    private int is_main;

    private String reciever;

    private String reciever_phone;

    private int checked;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void resetIsMain() {
        this.is_main = 0;
    }

    public void resetChecked() {
        this.checked = 0;
    }

    public void updateAddress(String deliver_detail_address, String reciever, String reciever_phone, int is_main) {
        this.deliver_detail_address = deliver_detail_address;
        this.reciever = reciever;
        this.reciever_phone = reciever_phone;
        this.is_main = is_main;
    }
}