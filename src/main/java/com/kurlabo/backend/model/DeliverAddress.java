package com.kurlabo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class DeliverAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="deliver_address_id")
    private Long id;

    private String deliver_address;

    private String reciever;

    private String reciever_phone;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member; // 받으실 분

    private boolean is_main; // 0: 아님 1: 기본배송지
    // 배송지 등록할 때 다 0ㅇ으로 만들고 지금 등록한 게 1이 됨
}