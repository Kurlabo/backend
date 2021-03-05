package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.goods.ProductDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.repository.ReviewRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class GoodsServiceTest {

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void goodDetailTest() {
        Pageable pageable = PageRequest.of(0, 7);
        productRepository.findById((321L));
        ProductDto re = goodsService.goodDetail(pageable,321L);

        assertThat (re.getProduct_id().equals(321L));
    }

    @Test
    @DisplayName("도움이 돼요 테스트")
    void reviewHelpCountTest() {
        Review review = reviewRepository.findById(1L).orElseThrow(
                ResourceNotFoundException::new
        );

        goodsService.reviewHelpCount(review);

        Review after = reviewRepository.findById(1L).orElseThrow(
                ResourceNotFoundException::new
        );

        //assertThat (after.getHelp()).isEqualTo(13L);
    }

    @Test
    @DisplayName("조회수 테스트")
    void reviewCountTest() {
        Review review = reviewRepository.findById(1L).orElseThrow(
                ResourceNotFoundException::new
        );

        goodsService.reviewUpdateCnt(review);

        Review after = reviewRepository.findById(1L).orElseThrow(
                ResourceNotFoundException::new
        );

        //assertThat (after.getCnt()).isEqualTo(6L);
    }
}