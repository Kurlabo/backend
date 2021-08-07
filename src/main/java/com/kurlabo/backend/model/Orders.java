package com.kurlabo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orders_id")
    private Long id;
    private String sender;
    private String orderer;
    private String reciever;
    private String reciever_phone;
    private String reciever_post;
    private String reciever_place;
    private String reciever_visit_method;
    private LocalDate checkoutDate;
    private String checkout;
    private String delivery_condition;
    private String arrived_alarm;
    private int total_price;
    private int total_discount_price;
    private String status;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setStatus(String status){
        this.status = status;
    }
}
