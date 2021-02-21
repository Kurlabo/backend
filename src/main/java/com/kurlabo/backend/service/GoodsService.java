package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.goods.GoodsListResponseDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.ProductPageableRepository;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.dto.goods.ProductDto;
import com.kurlabo.backend.dto.goods.RelatedProductDto;
import com.kurlabo.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final ProductRepository productRepository;
    private final ProductPageableRepository productPageableRepository;
    private final ReviewRepository reviewRepository;

    public ProductDto goodDetail(Pageable pageable, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // Page<Review> reviews = reviewRepository.findAllByProduct(product, pageable);

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

        //productDto.setReviews(reviews);
        productDto.setRelated_product(list);

        return productDto;
    }

    public List<GoodsListResponseDto> getGoodsList(int category, Pageable pageable){
        List<GoodsListResponseDto> responseDtos = new ArrayList<>();
        List<Product> productList = new ArrayList<>();

        if(category >= 0 && category <= 166){           // 자식 카테고리
//            productList = productRepository.findByCategory(category);

//             임시적으로 같은 상품 데이터 3개 뿌려주고 나중에 리팩토링 해야 함.
            productList = productPageableRepository.findByCategoryPageable(category, Pageable.unpaged());
            productList.addAll(productPageableRepository.findByCategoryDescPageable(category, Pageable.unpaged()));
        } else if(category == 200){                     // 신상품
            productList = setRandomProducts(pageable);
        } else if(category == 300){                     // 베스트
            productList = setRandomProducts(pageable);
        } else if(category == 400){                     // 알뜰쇼핑
            productList = productPageableRepository.findByDiscount_percentPageable(pageable);
        } else {                                        // 부모카테고리
            productList = getProducts(category, productList, pageable);
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

        return responseDtos;
    }

    private List<Product> getProducts(int category, List<Product> productList, Pageable pageable) {
        switch (category){
            case 1000: productList = productPageableRepository.findByCategoryVegePageable(pageable);            break;
            case 1001: productList = productPageableRepository.findByCategoryFruitsPageable(pageable);          break;
            case 1002: productList = productPageableRepository.findByCategorySeafoodPageable(pageable);         break;
            case 1003: productList = productPageableRepository.findByCategoryMeatPageable(pageable);            break;
            case 1004: productList = productPageableRepository.findByCategoryMaindishPageable(pageable);        break;
            case 1005: productList = productPageableRepository.findByCategoryFastFoodPageable(pageable);        break;
            case 1006: productList = productPageableRepository.findByCategoryNoodleoilPageable(pageable);       break;
            case 1007: productList = productPageableRepository.findByCategoryDringPageable(pageable);           break;
            case 1008: productList = productPageableRepository.findByCategorySnacksPageable(pageable);          break;
            case 1009: productList = productPageableRepository.findByCategoryBakeryPageable(pageable);          break;
            case 1010: productList = productPageableRepository.findByCategoryHealthFoodPageable(pageable);      break;
            case 1011: productList = productPageableRepository.findByCategoryLivingPageable(pageable);          break;
            case 1012: productList = productPageableRepository.findByCategoryBeautyPageable(pageable);          break;
            case 1013: productList = productPageableRepository.findByCategoryKitchenPageable(pageable);         break;
            case 1014: productList = productPageableRepository.findByCategoryHomeAppliancePageable(pageable);   break;
            case 1015: productList = productPageableRepository.findByCategoryBabyKizPageable(pageable);         break;
            case 1016: productList = productPageableRepository.findByCategoryPetPageable(pageable);             break;
        }
        return productList;
    }

    private List<Product> setRandomProducts(Pageable pageable){      // 일단 데이터 양이 많지 않으니 만들어 놓지만 절대 쓰면 안됨.
        List<Product> lists = productPageableRepository.findAllPageable(pageable);
        Collections.shuffle(lists);
        return lists;
    }

    public Long reviewHelpCount(Long review_id) {
        return Long.valueOf(reviewRepository.updateHelpCnt(review_id));
    }

    public Long reviewUpdateCnt(Long review_id) {
        return Long.valueOf(reviewRepository.updateViewCnt(review_id));
    }
}
