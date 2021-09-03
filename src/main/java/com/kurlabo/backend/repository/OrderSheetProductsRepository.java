package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Order_Sheet_Products;
import com.kurlabo.backend.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderSheetProductsRepository extends JpaRepository<Order_Sheet_Products, Long> {
    List<Order_Sheet_Products> findAllByOrders(Orders orders);

    @Query("select p from Order_Sheet_Products p where p.orders = :orders and p.product_id = :productId")
    Optional<Order_Sheet_Products> findByOrdersAndProduct_id(Orders orders, Long productId);
}
