package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long > {

    List<Review> findAllByProduct(Product product);

}