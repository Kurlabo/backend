package com.kurlabo.backend.repository;

import com.kurlabo.backend.dto.goods.RelatedProductDtoProjection;
import com.kurlabo.backend.dto.main.MainPageProductDtoProjection;
import com.kurlabo.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select product_id, detail_image_url, sticker_image_url, name, original_price, discounted_price, discount_percent" +
            " from product order by rand() limit :limitCnt", nativeQuery = true)
    List<MainPageProductDtoProjection> findAllRandom(int limitCnt);

    @Query(value = "select product_id, detail_image_url, sticker_image_url, name, original_price, discounted_price, discount_percent" +
            " from product where discount_percent > 0 order by rand() limit ?1", nativeQuery = true)
    List<MainPageProductDtoProjection> findDiscountPercentOverZero(int max);

    @Query(value = "select product_id, detail_image_url, sticker_image_url, name, original_price, discounted_price, discount_percent" +
            " from product where category >= ?1 and category <= ?2 order by rand() limit 16", nativeQuery = true)
    List<MainPageProductDtoProjection> findRandProductCategoryRange(int min, int max);

    @Query(value = "select product_id, name, original_image_url, original_price, discounted_price" +
            " from product where category >= :min and category <= :max order by rand() limit 10", nativeQuery = true)
    List<RelatedProductDtoProjection> findRandRelatedProductList(@Param("min") int min, @Param("max")int max);

}