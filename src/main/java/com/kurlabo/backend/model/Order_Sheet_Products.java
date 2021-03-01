package com.kurlabo.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class Order_Sheet_Products {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_sheet_products_id")
    private Long id;
    private Long product_id;
    private String product_name;
    private int product_price;
    private int discounted_price;
    private int product_cnt;
    private String list_image_url;
    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;
}
