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

    public ProductDto getGoods(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + productId));

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
                .guides(combineGuides(product.getGuides()))
                .packing_type_text(product.getPacking_type_text())
                .related_product(findRelatedProductDtoList(product.getCategory() / 10 * 10, (product.getCategory() / 10 * 10) + 10))
                .build();
    }

    public ProductDto getGoodsDetail(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + productId));

        return ProductDto.builder()
                .product_img_url(product.getProduct_img_url())
                .detail_img_url(product.getDetail_img_url())
                .detail_context(product.getDetail_context())
                .build();
    }

    public Page<ReviewDto> getGoodsReview(Long productId, Pageable pageable) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + productId));
        List<Review> reviews = reviewRepository.findAllByProduct(product);
        List<ReviewDto> reviewList = new ArrayList<>();

        for(Review review: reviews){
            ReviewDto dto = ReviewDto.builder()
                    .review_id(review.getId())
                    .title(review.getTitle())
                    .content(review.getContent())
                    .writer(review.getWriter())
                    .regDate(review.getRegDate())
                    .help(review.getHelp())
                    .cnt(review.getCnt())
                    .build();
            reviewList.add(dto);
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), reviewList.size());
        return new PageImpl<>(reviewList.subList(start, end), pageable, reviewList.size());
    }

    public Page<GoodsListResponseDto> getGoodsList(int category, Pageable pageable){
        List<GoodsListResponseDto> responseDtoList;

        if(category >= 0 && category <= 166){
            responseDtoList = dynamicProductRepository.findBySmallCategory(category);
        } else if (category == 200 || category == 300) {
            responseDtoList = dynamicProductRepository.findCntProducts(90);
            Collections.shuffle(responseDtoList);
        } else if (category == 400) {
            responseDtoList = dynamicProductRepository.findDiscountPercentOverZero(80);
        } else if (category >= 1001 && category <= 1016){
            responseDtoList = dynamicProductRepository.findByBigCategory((category % 1000) * 10);
        } else {
            throw new DataNotFoundException("카테고리를 잘못 입력하였습니다.");
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseDtoList.size());
        return new PageImpl<>(responseDtoList.subList(start, end), pageable, responseDtoList.size());
    }

    private List<RelatedProductDtoProjection> findRelatedProductDtoList (int min, int max){
        return productRepository.findRandRelatedProductList(min, max);
    }

    private List<String> combineGuides(String guideStr){
        StringBuilder sb = new StringBuilder(guideStr);
        List<String> guideElements = new ArrayList<>();

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