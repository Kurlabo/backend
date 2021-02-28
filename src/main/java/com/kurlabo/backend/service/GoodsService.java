package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.goods.GoodsListResponseDto;
import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.dto.goods.ProductDto;
import com.kurlabo.backend.dto.goods.RelatedProductDto;
import com.kurlabo.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
    private final ReviewRepository reviewRepository;

    public ProductDto goodDetail(Pageable pageable, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        List<Review> reviews = reviewRepository.findAllByProduct(product);

        List<ReviewDto> reviewList = new ArrayList<>();
        for(Review review: reviews){
            ReviewDto list = new ReviewDto(
                    review.getReview_id(),
                    review.getMember().getId(),
                    review.getProduct().getId(),
                    review.getTitle(),
                    review.getContent(),
                    review.getMember().getName(),
                    review.getRegdate(),
                    review.getHelp(),
                    review.getCnt()
            );
            reviewList.add(list);
        }
        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), reviews.size());

        List<Product> related_product = new ArrayList<>(); // 상위 카테고리에서 아이템 랜덤으로 넣을 리스트
        List<RelatedProductDto> list = new ArrayList<>();
        List<Integer> intList = new ArrayList<>();
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

        int max = related_product.size() - 1;

        if (max > 20){
            max = 20;
        }

        for (int i = 0; i < max; i++) {
            int n = random.nextInt(related_product.size());
            while (intList.contains(n)){
                n = random.nextInt(related_product.size());
            }
            intList.add(n);

            Product getRelate = related_product.get(n);

            if(getRelate.getId().equals(id)){
                i--;
                continue;
            }

            list.add(new RelatedProductDto(
                    getRelate.getId(),
                    getRelate.getName(),
                    getRelate.getOriginal_image_url(),
                    getRelate.getOriginal_price(),
                    getRelate.getDiscounted_price()
            ));
        }

        String getGuides = product.getGuides().replace('|','"');
        getGuides = getGuides.replace('\'',' ')
                .replace("[", "")
                .replace("]", "");

        String[] array = getGuides.split(",");
        List<String> getGuide = new ArrayList<>();

        for(int i = 0; i < array.length; i++) {
            getGuide.add(array[i]);
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
        // productDto.setDetail_image_url(product.getDetail_image_url());
        productDto.setSticker_image_url(product.getSticker_image_url());
        productDto.setDetail_img_url(product.getDetail_img_url());
        productDto.setDetail_title(product.getDetail_title());
        productDto.setDetail_context(product.getDetail_context());
        productDto.setProduct_img_url(product.getProduct_img_url());
        productDto.setGuides(getGuide);
        productDto.setPacking_type_text(product.getPacking_type_text());

        productDto.setReviews(new PageImpl<>(reviewList.subList(start, end), pageable, reviews.size()));
        productDto.setRelated_product(list);

        return productDto;
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