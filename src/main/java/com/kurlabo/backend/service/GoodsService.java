package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.goods.GoodsListResponseDto;
import com.kurlabo.backend.dto.goods.ProductDto;
import com.kurlabo.backend.dto.goods.RelatedProductDtoProjection;
import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.repository.ReviewRepository;
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

    public ProductDto goodDetail(Pageable pageable, Long id) {
        Product product = productRepository.findById(id).orElseThrow(ResourceNotFoundException::new);

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
        List<GoodsListResponseDto> responseDtos = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        if(category >= 0 && category <= 166){           // 자식 카테고리
//            productList = productRepository.findByCategory(category);

//             임시적으로 같은 상품 데이터 3개 뿌려주고 나중에 리팩토링 해야 함.
            productList = productRepository.findByCategory(category);
            productList.addAll(productRepository.findByCategoryDesc(category));
        } else if(category == 200){                     // 신상품
            productList = setRandomProducts();
        } else if(category == 300){                     // 베스트
            productList = setRandomProducts();
        } else if(category == 400){                     // 알뜰쇼핑
            productList = productRepository.findByDiscount_percent();
        } else {                                        // 부모카테고리
            productList = getProducts(category, productList);
        }

        for(Product list: productList){
            responseDtos.add(new GoodsListResponseDto(
                    list.getId(),
                    list.getOriginal_image_url(),
                    list.getSticker_image_url(),
                    list.getName(),
                    list.getOriginal_price(),
                    list.getDiscounted_price(),
                    list.getDiscount_percent(),
                    list.getShort_description()
            ));
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseDtos.size());
        return new PageImpl<>(responseDtos.subList(start, end), pageable, responseDtos.size());
    }

    private List<RelatedProductDtoProjection> findRelatedProductDtoList (int min, int max){
        return productRepository.findRelatedProductDtoList(min, max);
    }

    private List<String> combineGuides(String guideStr){
        StringBuilder sb = new StringBuilder(guideStr);

        sb.delete(0, 2);
        sb.delete(sb.length() - 2, sb.length());

        StringTokenizer st = new StringTokenizer(sb.toString(), "\",");
        List<String> guideElements = new ArrayList<>();

        while(st.hasMoreTokens()){
            guideElements.add(st.nextToken());
        }

        return guideElements;
    }

    private List<Product> getProducts(int category, List<Product> productList) {
        switch (category){
            case 1000: productList = productRepository.findByCategoryVege();            break;
            case 1001: productList = productRepository.findByCategoryFruits();          break;
            case 1002: productList = productRepository.findByCategorySeafood();         break;
            case 1003: productList = productRepository.findByCategoryMeat();            break;
            case 1004: productList = productRepository.findByCategoryMaindish();        break;
            case 1005: productList = productRepository.findByCategoryFastFood();        break;
            case 1006: productList = productRepository.findByCategoryNoodleoil();       break;
            case 1007: productList = productRepository.findByCategoryDring();           break;
            case 1008: productList = productRepository.findByCategorySnacks();          break;
            case 1009: productList = productRepository.findByCategoryBakery();          break;
            case 1010: productList = productRepository.findByCategoryHealthFood();      break;
            case 1011: productList = productRepository.findByCategoryLiving();          break;
            case 1012: productList = productRepository.findByCategoryBeauty();          break;
            case 1013: productList = productRepository.findByCategoryKitchen();         break;
            case 1014: productList = productRepository.findByCategoryHomeAppliance();   break;
            case 1015: productList = productRepository.findByCategoryBabyKiz();         break;
            case 1016: productList = productRepository.findByCategoryPet();             break;
        }
        return productList;
    }

    private List<Product> setRandomProducts(){      // 일단 데이터 양이 많지 않으니 만들어 놓지만 절대 쓰면 안됨.
        List<Product> lists = productRepository.findAll();
        Collections.shuffle(lists);
        return lists;
    }

    public void reviewHelpCount(Review review) {
        review.increaseHelp();
    }

    public void reviewUpdateCnt(Review review) {
        review.increaseCount();
    }
}