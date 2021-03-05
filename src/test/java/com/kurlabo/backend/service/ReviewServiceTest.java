package com.kurlabo.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Orders;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.OrderRepository;
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

import java.time.LocalDate;
import java.util.List;


@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ReviewServiceTest {
    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("리뷰 작성 테스트")
    void createTest() {
        Member m = memberRepository.findById(1L).orElseThrow(ResourceNotFoundException::new);
        Product p = productRepository.findById(1L).orElseThrow(ResourceNotFoundException::new);

        ReviewDto review = new ReviewDto();
        review.setContent("냠냠굿1");
        review.setTitle("냠냠굿");
        review.setRegdate(LocalDate.now());
        review.setWriter(m.getName());
        review.setMember_id(m.getId());
        review.setProduct_id(p.getId());

        reviewService.create(p.getId(), review);

        // assertThat (re.getReview_id().equals(24L));
    }

    @Test
    @DisplayName("리뷰 리스트 테스트")
    void reviewListTest() throws JsonProcessingException {
        Member m = memberRepository.findById(1L).orElseThrow(ResourceNotFoundException::new);
        Product p = productRepository.findById(2L).orElseThrow(ResourceNotFoundException::new);
        Review r = reviewRepository.findById(1L).orElseThrow(ResourceNotFoundException::new);
        List<Orders> o = orderRepository.findByMember(m);
        Pageable pageable = PageRequest.of(0, 10);

        Review review = new Review();
        review.setContent("리뷰작성테스트");
        review.setTitle("리뷰작성테스트");
        review.setRegdate(LocalDate.now());
        review.setWriter(m.getName());
        review.setMember(m);
        review.setProduct(p);

        reviewService.reviewList(1);

        // assertThat (re.getReview_id().equals(24L));
    }
}