package com.kurlabo.backend.model;

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
    private LocalDate checkoutDate;
    private String checkout;
    private String delivery_condition;
    private String arrived_alarm;
    private String product_id_cnt_list;
    private int total_price;
    private int total_discount_price;
    private String status;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
//    @OneToMany(mappedBy = "orders",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private List<Order_Sheet_Products> order_sheet_products;

//    public void addOrderSheetProducts(Order_Sheet_Products osp){
//        order_sheet_products.add(osp);
//        osp.setOrders(this);
//    }
}
