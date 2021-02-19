package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.ReviewDto;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void test() {
        Optional<Orders> order = orderRepository.findById(1000L);
        order.ifPresent(selectOrder -> {
        });
    }

    @DisplayName("Get Review")
    @Test
    void creatTest() {
        when (memberRepository.findById(1L)).thenReturn(member());
        when (productRepository.findById(1L)).thenReturn(product());

        reviewService.conditionsChk(reviewDto());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    void findReviewByProductIdTest() {
        when(reviewRepository.findByProduct(1L)).thenReturn(review());

        Review review = reviewService.findReviewByProductId(1L);

        assertThat(review.getTitle().equals("후기제목"));
        assertThat(review.getContent().equals("후기"));

    }

    private Optional<Member> member() {
        Member member = new Member();

        member.setId(1L);
        member.setName("test");
        member.setPassword("1234");

        return Optional.of(member);
    }

    private Optional<Product> product() {
        Product product = new Product();

        product.setId(1L);

        return Optional.of(product);
    }

    private ReviewDto reviewDto() {
        return ReviewDto.builder()
                .title("후기제목")
                .content("후기")
                .writer("작성자")
                .help(3L)
                .cnt(10L)
                .member_id(member().get().getId())
                .product_id(product().get().getId())
                .regdate(LocalDate.now())
                .build();
    }

    private Optional<Review> review() {
        return Optional.of(Review.builder()
                .title("후기제목")
                .content("후기")
                .writer("작성자")
                .help(3L)
                .cnt(10L)
                .member(member().get())
                .product(product().get())
                .regdate(LocalDate.now())
                .build());
    }





}