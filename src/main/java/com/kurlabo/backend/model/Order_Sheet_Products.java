package com.kurlabo.backend.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
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

    private int review_status;

    public void updateReviewStatus() {
        this.review_status = 1;
    }
}
