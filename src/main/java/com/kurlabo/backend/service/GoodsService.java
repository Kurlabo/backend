package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.goods.ProductDto;
import com.kurlabo.backend.dto.goods.RelatedProductDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public ProductDto goodDetail(Pageable pageable, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // review info -> id, title, content, writer, regdate, help, cnt, product, member
        Page<Review> reviews = reviewRepository.findAllByProduct(product, pageable);
//        List<Review> reviewsResults = reviews.getContent().stream().map(
//                review -> sourceToDestination(review, new Review())
//        ).collect(Collectors.toList());

        List<Product> related_product = new ArrayList<>(); // 상위 카테고리에서 아이템 랜덤으로 넣을 리스트
        List<RelatedProductDto> list = new ArrayList<>();
        Random random = new Random();

        switch (product.getCategory()/10) {
            case 0:
                related_product = productRepository.findByCategoryVege();
                break;
            case 1:
                related_product = productRepository.findByCategoryFruits();
                break;
            case 2:
                related_product = productRepository.findByCategorySeafood();
                break;
            case 3:
                related_product = productRepository.findByCategoryMeat();
                break;
            case 4:
                related_product = productRepository.findByCategoryMaindish();
                break;
            case 5:
                related_product = productRepository.findByCategoryFastFood();
                break;
            case 6:
                related_product = productRepository.findByCategoryNoodleoil();
                break;
            case 7:
                related_product = productRepository.findByCategoryDring();
                break;
            case 8:
                related_product = productRepository.findByCategorySnacks();
                break;
            case 9:
                related_product = productRepository.findByCategoryBakery();
                break;
            case 10:
                related_product = productRepository.findByCategoryHealthFood();
                break;
            case 11:
                related_product = productRepository.findByCategoryLiving();
                break;
            case 12:
                related_product = productRepository.findByCategoryBeauty();
                break;
            case 13:
                related_product = productRepository.findByCategoryKitchen();
                break;
            case 14:
                related_product = productRepository.findByCategoryHomeAppliance();
                break;
            case 15:
                related_product = productRepository.findByCategoryBabyKiz();
                break;
            case 16:
                related_product = productRepository.findByCategoryPet();
                break;
        }

        for (int i = 0; i < 20; i++) {
            int n = random.nextInt(related_product.size());
            Product getRelate = related_product.get(n);

            list.add(new RelatedProductDto(
                    getRelate.getId(),
                    getRelate.getList_image_url(),
                    getRelate.getName(),
                    getRelate.getOriginal_price(),
                    getRelate.getDiscounted_price()
            ));
        }

        ProductDto productDto = new ProductDto();
        productDto.setProduct_id(product.getId());
        productDto.setName(product.getName());
        productDto.setShort_description(product.getShort_description());
        productDto.setUnit_text(product.getUnit_text());
        productDto.setWeight(product.getWeight());
        productDto.setOrigin(product.getOrigin());
        productDto.setContactant(product.getContactant());
        productDto.setBrand_title(product.getBrand_title());
        productDto.setExpiration_date(product.getExpiration_date());
        productDto.setDelivery_time_type_text(product.getDelivery_time_type_text());
        productDto.setOriginal_price(product.getOriginal_price());
        productDto.setDiscounted_price(product.getDiscounted_price());
        productDto.setDiscount_percent(product.getDiscount_percent());
        productDto.setDiscount_end_datetime(product.getDiscount_end_datetime());
        productDto.setOriginal_image_url(product.getOriginal_image_url());
        productDto.setMain_image_url(product.getMain_image_url());
        productDto.setList_image_url(product.getList_image_url());
        productDto.setDetail_image_url(product.getDetail_image_url());
        productDto.setSticker_image_url(product.getSticker_image_url());
        productDto.setDetail_title(product.getDetail_title());
        productDto.setDetail_context(product.getDetail_context());
        productDto.setProduct_img_url(product.getProduct_img_url());

        productDto.setReviews(reviews);
        productDto.setRelated_product(list);

        return productDto;
    }

    public Long reviewHelpCount(Long review_id) {
        return Long.valueOf(reviewRepository.updateHelpCnt(review_id));
    }

    public Long reviewUpdateCnt(Long review_id) {
        return Long.valueOf(reviewRepository.updateViewCnt(review_id));
    }
}
