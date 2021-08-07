package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.goods.GoodsListResponseDto;
import com.kurlabo.backend.dto.goods.ProductDto;
import com.kurlabo.backend.dto.goods.RelatedProductDtoProjection;
import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.repository.ReviewRepository;
import com.kurlabo.backend.repository.dynamic.DynamicProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final DynamicProductRepository dynamicProductRepository;

    public ProductDto goodDetail(Pageable pageable, Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + id));

        Page<Review> reviews = reviewRepository.findAllByProduct(product, pageable);

        List<ReviewDto> reviewList = new ArrayList<>();
        for(Review review: reviews){
            ReviewDto dto = ReviewDto.builder()
                    .review_id(review.getReview_id())
                    .member_id(review.getMember().getId())
                    .product_id(review.getProduct().getId())
                    .title(review.getTitle())
                    .content(review.getContent())
                    .writer(review.getMember().getName())
                    .regdate(review.getRegdate())
                    .help(review.getHelp())
                    .cnt(review.getHelp())
                    .build();
            reviewList.add(dto);
        }

        return ProductDto.builder()
                .product_id(product.getId())
                .name(product.getName())
                .short_description(product.getShort_description())
                .unit_text(product.getUnit_text())
                .weight(product.getWeight())
                .origin(product.getOrigin())
                .contactant(product.getContactant())
                .brand_title(product.getBrand_title())
                .expiration_date(product.getExpiration_date())
                .delivery_time_type_text(product.getDelivery_time_type_text())
                .original_price(product.getOriginal_price())
                .discounted_price(product.getDiscounted_price())
                .discount_percent(product.getDiscount_percent())
                .discount_end_datetime(product.getDiscount_end_datetime())
                .original_image_url(product.getOriginal_image_url())
                .main_image_url(product.getMain_image_url())
                .list_image_url(product.getList_image_url())
                .sticker_image_url(product.getSticker_image_url())
                .detail_img_url(product.getDetail_img_url())
                .detail_context(product.getDetail_context())
                .product_img_url(product.getProduct_img_url())
                .guides(combineGuides(product.getGuides()))
                .packing_type_text(product.getPacking_type_text())
                .reviews(reviewList)
                .related_product(findRelatedProductDtoList(product.getCategory() / 10 * 10, (product.getCategory() / 10 * 10) + 10))
                .build();
    }

    public Page<GoodsListResponseDto> getGoodsList(int category, Pageable pageable){
        List<GoodsListResponseDto> responseDtos;

        if(category >= 0 && category <= 166){
            responseDtos = dynamicProductRepository.findBySmallCategory(category);
        } else if (category == 200 || category == 300) {
            responseDtos = dynamicProductRepository.findCntProducts(90);
            Collections.shuffle(responseDtos);
        } else if (category == 400) {
            responseDtos = dynamicProductRepository.findDiscountPercentOverZero(80);
        } else if (category >= 1001 && category <= 1016){
            responseDtos = dynamicProductRepository.findByBigCategory(setCategoryMinValue(category));
        } else {
            return null;
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseDtos.size());
        return new PageImpl<>(responseDtos.subList(start, end), pageable, responseDtos.size());
    }

    public void reviewHelpCount(Review review) {
        review.increaseHelp();
    }

    private int setCategoryMinValue(int category){
        return (category % 1000) * 10;
    }

    private List<RelatedProductDtoProjection> findRelatedProductDtoList (int min, int max){
        return productRepository.findRandRelatedProductList(min, max);
    }

    private List<String> combineGuides(String guideStr){
        StringBuilder sb = new StringBuilder(guideStr);
        List<String> guideElements = new ArrayList<>();

        System.out.println("guideStr : " + guideStr);

        if(guideStr.equals("[]")){
            return guideElements;
        }

        sb.delete(0, 2);
        sb.delete(sb.length() - 2, sb.length());

        StringTokenizer st = new StringTokenizer(sb.toString(), "\",");


        while(st.hasMoreTokens()){
            guideElements.add(st.nextToken());
        }

        return guideElements;
    }
}