package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.testdto.ReviewDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Orders;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.Review;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.OrderRepository;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;

    private final MemberRepository memberRepository;

    private final ProductRepository productRepository;

    private final OrderRepository orderRepository;

    public ReviewDto conditionsChk(ReviewDto reviewDto) {
        // 사용자 정보 없음
        Member member = memberRepository.findById(reviewDto.getMember_id()).orElseThrow(
                ResourceNotFoundException::new
        );

        // 상품 정보 없음
        productRepository.findById(reviewDto.getProduct_id()).orElseThrow(
                ResourceNotFoundException::new
        );

        LocalDate fromDate = LocalDate.now(); // 현재시간 yyyy-MM-dd
        List<Orders> orderList = orderRepository.findByMember(member);
        List<Long> alreadyReview = new ArrayList<>();
        List<Long> reviewsToWrite = new ArrayList<>();

        System.out.println(orderList.isEmpty());

        for (Orders order : orderList) {
            // order.setCheckout_date(LocalDate.of(2021, 1, 13));
            int dDay = (int) DAYS.between(fromDate, order.getCheckout_date()) * -1;

            // 상품 구매 30일 이후 체크
            if (dDay > 30) {
                System.out.println("상품후기는 상품을 구매하시고 배송완료된 회원 분만 한 달 내 작성 가능합니다.");
                throw new ResourceNotFoundException();
            }

            // 리뷰를 이미 남긴 사용자
            if (order.getMember().getId().equals(reviewDto.getMember_id())) {
                System.out.println("이미 작성한 상품 후기");
                alreadyReview.add(reviewDto.getMember_id());
                alreadyReview.add(reviewDto.getProduct_id());
            }

            String product = order.getProduct_id_list()
                    .replace("[","")
                    .replace(","," ")
                    .replace("]","");
            String [] productIdList = product.split(" ");
            reviewsToWrite.add(order.getMember().getId());

            for (String products : productIdList) {
                reviewsToWrite.add(Long.parseLong(products));
            }

        } // end for

        create(reviewDto);

        reviewDto.setAlreadyReview(alreadyReview);
        reviewDto.setReviewsToWrite(reviewsToWrite);

        return reviewDto;
    }

    public void create(ReviewDto reviewDto) {
        Member member = memberRepository.findById(reviewDto.getMember_id()).orElseThrow(
                ResourceNotFoundException::new
        );

        Product product = productRepository.findById(reviewDto.getProduct_id()).orElseThrow(
                ResourceNotFoundException::new
        );

        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setWriter(reviewDto.getWriter());
        review.setCnt(reviewDto.getCnt());
        review.setHelp(reviewDto.getHelp());
        review.setRegdate(LocalDate.now());
        review.setMember(member);
        review.setProduct(product);

        reviewRepository.save(review);
    }

    // 상품 별 후기
    public Review findReviewByProductId(Long productId) {
        return reviewRepository.findByProduct(productId)
                .orElseThrow(ResourceNotFoundException::new);
    }



}
