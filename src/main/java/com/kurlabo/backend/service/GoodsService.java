package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.ProductDto;
import com.kurlabo.backend.dto.goods.GoodsListResponseDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.ProductPageableRepository;
import com.kurlabo.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final ProductRepository productRepository;
    private final ProductPageableRepository productPageableRepository;

    @Transactional
    public ProductDto goodDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        List<Product> related_product = productRepository.findByCategory(product.getCategory());

        ProductDto productDto = new ProductDto();
        productDto.setProduct_id(product.getId());
        productDto.setData(product.getData());
        productDto.setRelated_product(related_product);

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

}
