package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.review.ReviewListDto;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰 작성 조건 체크
    public Review conditionsChk(Review review) {
        // 사용자 정보 없음
        Member member = memberRepository.findById(review.getMember().getId()).orElseThrow(
                ResourceNotFoundException::new
        );

        // 상품 정보가 없음
        productRepository.findById(review.getProduct().getId()).orElseThrow(
                ResourceNotFoundException::new
        );

        LocalDate fromDate = LocalDate.now(); // 현재시간 yyyy-MM-dd
        List<Orders> orderList = orderRepository.findByMember(member);

        for (Orders order : orderList) {
            int dDay = (int) DAYS.between(fromDate, order.getCheckout_date()) * -1;

            // 상품 구매 후 30일 이상 지남
            if (dDay > 30) {
                System.out.println("상품후기는 상품을 구매하시고 배송완료된 회원 분만 한 달 내 작성 가능합니다.");
                throw new ResourceNotFoundException();
            }
        } // end for

        create(review);
        return review;
    }

    // 리뷰 리스트 리턴
//    public Page<ReviewListDto> reviewList(Pageable pageable, Review review) {
//         List<Long> writableReviews = new ArrayList<>(); // 작성가능 후기
//         List<Long> writtenReviews = new ArrayList<>(); // 작성완료 후기
//
//        // 사용자 검색
//        Member member = memberRepository.findById(review.getMember().getId()).orElseThrow(
//                ResourceNotFoundException::new
//        );
//
//        //productRepository.findById(review.getProduct().getId(), pageable);
//
//        List<Orders> orderByMember = orderRepository.findByMember(member);
//        Long orderByMemberId = orderByMember.get(0).getMember().getId();
//
//        writableReviews.add(orderByMemberId);
//        writtenReviews.add(orderByMemberId);
//
//        for (Orders order : orderByMember) {
//            // 주문내역의 사용자 == 리뷰남긴 사용자 면 작성완료 후기 리스트에 추가
//            if (order.getMember().getId().equals(review.getMember().getId())) {
//                writtenReviews.add(review.getProduct().getId());
//            }
//            else {
//            // 아니라면 주문내역 상품 리스트 작성가능 후기리스트에 추가
//                String x = order.getProduct_id_cnt_list()
//                        .replace("[", "")
//                        .replace(",", " ")
//                        .replace("]", "");
//                String[] productIdList = x.split(" ");
//
//                for (String products : productIdList) {
//                    writableReviews.add(Long.parseLong(products));
//                } // end for
//            }
//
//        } // end for
//
//        ReviewListDto reviewListDto = new ReviewListDto();
//        reviewListDto.setWritableReviews(writableReviews);
//        reviewListDto.setWrittenReviews(writtenReviews);
//
//        return (Page<ReviewListDto>) reviewListDto;
//    }

    // 리뷰작성
    public void create(Review review) {
        Member member = memberRepository.findById(review.getMember().getId()).orElseThrow(
                ResourceNotFoundException::new
        );

        Product product = productRepository.findById(review.getProduct().getId()).orElseThrow(
                ResourceNotFoundException::new
        );

        Review newReview = new Review();
        newReview.setTitle(review.getTitle());
        newReview.setContent(review.getContent());
        newReview.setWriter(review.getWriter());
        newReview.setRegdate(LocalDate.now());
        newReview.setMember(member);
        newReview.setProduct(product);

        reviewRepository.save(newReview);
    }


}
