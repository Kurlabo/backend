package com.kurlabo.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.review.ReviewListDto;
import com.kurlabo.backend.dto.review.ReviewProductDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.OrderRepository;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
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
        LocalDate chkDate = LocalDate.now().minusMonths(1L);
        List<Orders> orderList = orderRepository.findByMemberAndCheckoutDate(member, chkDate, fromDate);

        if (orderList.isEmpty()) {
            return null;
        } else {
            create(review);
        }

        return review;
    }

    // 주문번호, 상품명, 배송완료날짜(02월07일 배송완료), 구매수량, 상품이미지(list_img?)
    // 제품명, 후기제목, 후기내용, 도움이 돼요, 작성날짜
    public List<ReviewListDto> reviewList(Member member, Review review) throws JsonProcessingException {
        // 사용자 검색
        memberRepository.findById(member.getId()).orElseThrow(ResourceNotFoundException::new);

        ObjectMapper objectMapper = new ObjectMapper();
        List<Review> reviewChk = new ArrayList<>();
        List<ReviewListDto> list = new ArrayList<>();
        List<ReviewProductDto> orderReview = new ArrayList<>();
        List<Orders> orderList = orderRepository.findByMember(member); // 멤버의 주문 내역
        List<Orders> orders = new ArrayList<>();
        List<Long> orderProduct = new ArrayList<>();

        // 주문번호에서 특정 멤버가 주문한 상품의 정보만 뽑음
        for (Orders order : orderList) {
            orderReview = objectMapper.readValue(
                    order.getProduct_id_cnt_list(),
                    new TypeReference<List<ReviewProductDto>>() {}
            );


            // 상품 정보에서 상품 아이디만 따로 저장해
            for (ReviewProductDto oPList : orderReview ) {
                orderProduct.add(oPList.getProduct_id());
            } // end for
        } // end for

        // 상품아이디랑 멤버아이디로 리뷰 작성했는지 검사해
        for (Long oPId : orderProduct) {
            Product product = productRepository.findById(oPId).orElseThrow(ResourceNotFoundException::new);
            reviewChk = reviewRepository.findByMemberAndProductId(member, product);
            if(!reviewChk.isEmpty()) {
                for (Review r : reviewChk) {
                    ReviewListDto reviewListDto = new ReviewListDto();
                    reviewListDto.setProduct_id(r.getProduct().getId());
                    reviewListDto.setProduct_name(r.getProduct().getName());
                    reviewListDto.setTitle(r.getTitle());
                    reviewListDto.setContent(r.getContent());
                    reviewListDto.setHelp(r.getHelp());
                    reviewListDto.setRegdate(r.getRegdate());
                    list.add(reviewListDto);
                } // end for
            } else {
                for (ReviewProductDto x : orderReview) {
                    //if (x.getProduct_id().equals(product.getId())) {
                        ReviewListDto reviewListDto = new ReviewListDto();
                        // reviewListDto.setOrder_id(orders.get(0).);
                        reviewListDto.setProduct_id(product.getId());
                        reviewListDto.setProduct_name(product.getName());
                        // reviewListDto.setDelivery_condition(x.get);
                        reviewListDto.setCnt(x.getCnt());
                        reviewListDto.setMain_img_url(product.getMain_image_url());
                        list.add(reviewListDto);
                    //}
                }
            }
        } // end for

        System.out.println("list--- " + list );

        return null;
    }

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
        newReview.setHelp(0L);
        newReview.setCnt(0L);
        newReview.setMember(member);
        newReview.setProduct(product);

        reviewRepository.save(newReview);
    }
}
