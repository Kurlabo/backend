package com.kurlabo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    private String sender;
    private String orderer;
    private String reciever;
    private String reciever_phone;
    private String reciever_post;
    private String reciever_place;
    private String reciever_visit_method;
    private String product_id_list;
    private Date checkout_date;
    private String checkout;
    private String delivery_condition;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
