package com.kurlabo.backend.repository.dynamic;

import com.kurlabo.backend.dto.goods.GoodsListResponseDto;
import com.kurlabo.backend.dto.goods.QGoodsListResponseDto;
import com.kurlabo.backend.model.QProduct;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DynamicProductRepository {

    private final JPAQueryFactory queryFactory;

    QProduct qProduct = QProduct.product;

    public List<GoodsListResponseDto> findBySmallCategory(int category) {
        List<GoodsListResponseDto> list = queryFactory
                .select(new QGoodsListResponseDto(qProduct.id, qProduct.original_image_url, qProduct.sticker_image_url,
                        qProduct.name, qProduct.original_price, qProduct.discounted_price, qProduct.discount_percent, qProduct.short_description))
                .from(qProduct)
                .where(qProduct.category.eq(category))
                .fetch();
        List<GoodsListResponseDto> result = new ArrayList<>(list);
        Collections.reverse(list);
        result.addAll(list);
        return result;
    }

    public List<GoodsListResponseDto> findByBigCategory(int category) {
        return queryFactory
                .select(new QGoodsListResponseDto(qProduct.id, qProduct.original_image_url, qProduct.sticker_image_url,
                        qProduct.name, qProduct.original_price, qProduct.discounted_price, qProduct.discount_percent, qProduct.short_description))
                .from(qProduct)
                .where(qProduct.category.goe(category).and(qProduct.category.lt(category + 10)))
                .limit(90)
                .fetch();
    }

    public List<GoodsListResponseDto> findDiscountPercentOverZero(int maxCnt) {
        return queryFactory
                .select(new QGoodsListResponseDto(qProduct.id, qProduct.original_image_url, qProduct.sticker_image_url,
                        qProduct.name, qProduct.original_price, qProduct.discounted_price, qProduct.discount_percent, qProduct.short_description))
                .from(qProduct)
                .where(qProduct.discount_percent.gt(0))
                .limit(maxCnt)
                .fetch();
    }

    public List<GoodsListResponseDto> findCntProducts(int maxCnt) {
        return queryFactory
                .select(new QGoodsListResponseDto(qProduct.id, qProduct.original_image_url, qProduct.sticker_image_url,
                        qProduct.name, qProduct.original_price, qProduct.discounted_price, qProduct.discount_percent, qProduct.short_description))
                .from(qProduct)
                .limit(maxCnt)
                .fetch();
    }
}
