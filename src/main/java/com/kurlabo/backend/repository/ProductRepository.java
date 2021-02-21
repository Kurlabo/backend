package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Override
//    @Query("select p from Product p where p.member = :member and p.products_id = :product_id")
//    List<Product> findById(@Param("product_id") Long product_id, );
    @Query("select p.id, p.name, p.list_image_url, p.original_price, p.discounted_price " +
            "from Product p " +
            "where p.category = :category")
    List<Product> findByCategory(@Param("category") int category);
    @Query("select p from Product p where p.discount_percent > 0")
    List<Product> findByDiscount_percent();
    @Query("select p from Product p where p.category >= 0 and p.category <= 9")
    List<Product> findByCategoryVege();
    @Query("select p from Product p where p.category >= 10 and p.category <= 19")
    List<Product> findByCategoryFruits();
    @Query("select p from Product p where p.category >= 20 and p.category <= 29")
    List<Product> findByCategorySeafood();
    @Query("select p from Product p where p.category >= 30 and p.category <= 39")
    List<Product> findByCategoryMeat();
    @Query("select p from Product p where p.category >= 40 and p.category <= 49")
    List<Product> findByCategoryMaindish();
    @Query("select p from Product p where p.category >= 50 and p.category <= 59")
    List<Product> findByCategoryFastFood();
    @Query("select p from Product p where p.category >= 60 and p.category <= 69")
    List<Product> findByCategoryNoodleoil();
    @Query("select p from Product p where p.category >= 70 and p.category <= 79")
    List<Product> findByCategoryDring();
    @Query("select p from Product p where p.category >= 80 and p.category <= 89")
    List<Product> findByCategorySnacks();
    @Query("select p from Product p where p.category >= 90 and p.category <= 99")
    List<Product> findByCategoryBakery();
    @Query("select p from Product p where p.category >= 100 and p.category <= 109")
    List<Product> findByCategoryHealthFood();
    @Query("select p from Product p where p.category >= 110 and p.category <= 119")
    List<Product> findByCategoryLiving();
    @Query("select p from Product p where p.category >= 120 and p.category <= 129")
    List<Product> findByCategoryBeauty();
    @Query("select p from Product p where p.category >= 130 and p.category <= 139")
    List<Product> findByCategoryKitchen();
    @Query("select p from Product p where p.category >= 140 and p.category <= 149")
    List<Product> findByCategoryHomeAppliance();
    @Query("select p from Product p where p.category >= 150 and p.category <= 159")
    List<Product> findByCategoryBabyKiz();
    @Query("select p from Product p where p.category >= 600 and p.category <= 169")
    List<Product> findByCategoryPet();
}
