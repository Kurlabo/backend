package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.ProductDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    @Transactional
    public ProductDto goodDetail(Pageable pageable, Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // review info -> id, title, content, writer, regdate, help, cnt, product, member
        Page<Review> reviews = reviewRepository.findAllByProduct(product, pageable);

        List<Product> related_product = productRepository.findByCategory(product.getCategory());
        // 서브카테고리 번호로 상위카테고리 id 뽑아와서 관련 상품 리스트 만들기

//        List<Product> productList = new ArrayList<>();
//        for(int i = 0; i < category.size(); i++){
//            Product related_product = category.get(i).getId();
//            productList.add(related_product);
//        }

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
        productDto.setRelated_product(related_product);

        return productDto;
    }

    @Transactional
    public Long reviewHelpCount(Long review_id) {
        return reviewRepository.updateHelpCnt(review_id);
    }
}
