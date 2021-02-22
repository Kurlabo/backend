package com.kurlabo.backend.model;

import com.kurlabo.backend.dto.order.OrderProductDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;


@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
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
    private LocalDate checkout_date;
    private String checkout;
    private String delivery_condition;
    private String arrived_alarm;
    private String product_id_cnt_list;
    private int total_cost;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

}
