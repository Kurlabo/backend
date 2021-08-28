package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.enums.ReviewStatus;
import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.dto.review.ReviewListDto;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final OrderSheetProductsRepository orderSheetProductsRepository;
    private final ReviewRepository reviewRepository;

    // status 가 0이면 작성 가능 후기 리스트 찾기 / status 가 1이면 작성 완료 후기 리스트 찾기
    public List<ReviewListDto> reviewList(Long memberId, ReviewStatus status) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));

        List<ReviewListDto> reviewList = new ArrayList<>();
        List<Orders> orderList = ordersRepository.findByMember(member);

        // 회원이 주문한 주문내역들 반복문으로 검색
        for (Orders order : orderList) {
            List<Order_Sheet_Products> orderedProductsList = orderSheetProductsRepository.findAllByOrders(order);

            // 주문번호별 상품들 리뷰 작성했는지 검사
            for (Order_Sheet_Products orderedProduct : orderedProductsList) {
                // 주문상품 불러오기
                Product products = productRepository.findById(orderedProduct.getProduct_id()).orElseThrow(() ->
                        new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + orderedProduct.getProduct_id()));
                // 주문상품에서 회원이 작성한 리뷰 불러오기
                List<Review> writtenReviewList = reviewRepository.findByMemberAndProduct(member, products);

                if (status == ReviewStatus.WRITABLE && orderedProduct.getReview_status() == 0) { // 작성가능 리뷰
                    ReviewListDto reviewListDto = ReviewListDto.builder()
                            .order_id(orderedProduct.getOrders().getId())
                            .product_id(orderedProduct.getProduct_id())
                            .product_name(orderedProduct.getProduct_name())
                            .delivery_condition(orderedProduct.getOrders().getDelivery_condition())
                            .cnt(orderedProduct.getProduct_cnt())
                            .list_img_url(orderedProduct.getList_image_url())
                            .written(reviewConditionsCheck(member, products.getId()))
                            .build();
                    reviewList.add(reviewListDto);
                } else if (status == ReviewStatus.WRITTEN && orderedProduct.getReview_status() == 1) { // 작성완료 리뷰
                    for (Review list : writtenReviewList) {
                        ReviewListDto reviewListDto = ReviewListDto.builder()
                                .product_id(list.getProduct().getId())
                                .review_id(list.getReview_id())
                                .product_name(list.getProduct().getName())
                                .title(list.getTitle())
                                .content(list.getContent())
                                .help(list.getHelp())
                                .regdate(list.getRegdate())
                                .build();
                        reviewList.add(reviewListDto);
                    }
                }
            }
        }

        return reviewList;
    }

    // 리뷰작성
    @Transactional
    public void createReview(Long memberId, Long product_id, ReviewDto review) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));
        Product product = productRepository.findById(product_id).orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + product_id));

        if (reviewConditionsCheck(member, review.getProduct_id())) {
            Review newReview = Review.builder()
                    .title(review.getTitle())
                    .content(review.getContent())
                    .writer(review.getWriter())
                    .regdate(LocalDate.now())
                    .help(0L)
                    .cnt(0L)
                    .member(member)
                    .product(product)
                    .build();

            reviewRepository.save(newReview);
            Order_Sheet_Products order_sheet_products = new Order_Sheet_Products();
            order_sheet_products.updateReviewStatus();
        }
    }

    // 리뷰 작성 조건 체크
    private boolean reviewConditionsCheck(Member member, Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + productId));

        LocalDate fromDate = LocalDate.now();
        LocalDate chkDate = fromDate.minusMonths(1L);
        List<Orders> orderList = ordersRepository.findByMemberAndCheckoutDate(member, chkDate, fromDate);
        List<Review> reviews = reviewRepository.findByMemberAndProduct(member, product);

        if (orderList.isEmpty()) {
            return false;
        }

        return reviews.isEmpty();
    }
}