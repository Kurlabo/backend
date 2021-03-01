package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Order_Sheet_Products;
import com.kurlabo.backend.model.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderSheetProductsRepository extends JpaRepository<Order_Sheet_Products, Long> {
    List<Order_Sheet_Products> findAllByOrders(Orders orders);
}
