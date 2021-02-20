package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Favorite;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Orders;
import com.kurlabo.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("select p.id, p.name, p.list_image_url, p.original_price, p.discounted_price " +
            "from Product p " +
            "where p.category = :category")
    List<Product> findByCategory(@Param("category") int category);

//    @Override
//    @Query("select p from Product p where p.member = :member and p.products_id = :product_id")
//    List<Product> findById(@Param("product_id") Long product_id, );
}
