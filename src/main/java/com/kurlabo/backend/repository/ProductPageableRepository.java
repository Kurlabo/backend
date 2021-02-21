package com.kurlabo.backend.repository;

import com.kurlabo.backend.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface ProductPageableRepository extends JpaRepository<Product, Long> {

    @Query("select p from Product p")
    List<Product> findAllPageable(Pageable pageable);

    @Query("select p from Product p where p.category = :category")
    List<Product> findByCategoryPageable(int category, Pageable pageable);

    @Query("select p from Product p where p.category = :category order by p.id desc")
    List<Product> findByCategoryDescPageable(int category, Pageable pageable);

    @Query("select p from Product p where p.discount_percent > 0")
    List<Product> findByDiscount_percentPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 0 and p.category <= 9")
    List<Product> findByCategoryVegePageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 10 and p.category <= 19")
    List<Product> findByCategoryFruitsPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 20 and p.category <= 29")
    List<Product> findByCategorySeafoodPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 30 and p.category <= 39")
    List<Product> findByCategoryMeatPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 40 and p.category <= 49")
    List<Product> findByCategoryMaindishPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 50 and p.category <= 59")
    List<Product> findByCategoryFastFoodPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 60 and p.category <= 69")
    List<Product> findByCategoryNoodleoilPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 70 and p.category <= 79")
    List<Product> findByCategoryDringPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 80 and p.category <= 89")
    List<Product> findByCategorySnacksPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 90 and p.category <= 99")
    List<Product> findByCategoryBakeryPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 100 and p.category <= 109")
    List<Product> findByCategoryHealthFoodPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 110 and p.category <= 119")
    List<Product> findByCategoryLivingPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 120 and p.category <= 129")
    List<Product> findByCategoryBeautyPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 130 and p.category <= 139")
    List<Product> findByCategoryKitchenPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 140 and p.category <= 149")
    List<Product> findByCategoryHomeAppliancePageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 150 and p.category <= 159")
    List<Product> findByCategoryBabyKizPageable(Pageable pageable);

    @Query("select p from Product p where p.category >= 160 and p.category <= 169")
    List<Product> findByCategoryPetPageable(Pageable pageable);
}
