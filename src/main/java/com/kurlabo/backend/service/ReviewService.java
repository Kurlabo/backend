package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.dto.review.ReviewListDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final OrderRepository orderRepository;
    private final OrderSheetProductsRepository orderSheetProductsRepository;
    private final ReviewRepository reviewRepository;

    // 리뷰 작성 조건 체크
    public boolean conditionsChk(Long pId) {
        Member member = memberRepository.findById(1L).orElseThrow(
                ResourceNotFoundException::new
        );

        // 사용자 정보 없음
//        Member member = memberRepository.findById(reviewDto.getMember_id()).orElseThrow(
//                ResourceNotFoundException::new
//        );

        // 상품 정보가 없음
        Product product = productRepository.findById(pId).orElseThrow(
                ResourceNotFoundException::new
        );

        LocalDate fromDate = LocalDate.now();
        LocalDate chkDate = fromDate.minusMonths(1L);
        List<Orders> orderList = orderRepository.findByMemberAndCheckoutDate(member, chkDate, fromDate);
        List<Review> reviews = reviewRepository.findByMemberAndProduct(member, product);

        if (orderList.isEmpty()) {
            return false;
        }

        if (!reviews.isEmpty()) {
            return false;
        }

        return true;
    }

    public List<ReviewListDto> reviewList(int stat) {
        // 사용자 검색
        // memberRepository.findById(member.getId()).orElseThrow(ResourceNotFoundException::new);
        Member member = memberRepository.findById(1L).orElseThrow(ResourceNotFoundException::new);


        List<ReviewListDto> list = new ArrayList<>();
        List<Orders> orderList = orderRepository.findByMember(member);

        for (Orders order : orderList) {
            List<Order_Sheet_Products> productsList = orderSheetProductsRepository.findAllByOrders(order);

            for (Order_Sheet_Products product : productsList) {
                Product products = productRepository.findById(product.getProduct_id()).orElseThrow(ResourceNotFoundException::new);
                List<Review> reviewChk = reviewRepository.findByMemberAndProduct(member, products);

                if (stat == 0 && product.getReview_status() == 0) { // 작성가능 리뷰
                    ReviewListDto reviewListDto = new ReviewListDto();
                    reviewListDto.setWritten(conditionsChk(products.getId()));
                    reviewListDto.setOrder_id(product.getOrders().getId());
                    reviewListDto.setProduct_id(product.getId());
                    reviewListDto.setProduct_name(product.getProduct_name());
                    reviewListDto.setDelivery_condition(product.getOrders().getDelivery_condition());
                    reviewListDto.setCnt(product.getProduct_cnt());
                    reviewListDto.setList_img_url(product.getList_image_url());
                    list.add(reviewListDto);
                } else if (stat == 1 && product.getReview_status() == 1) { // 작성완료 리뷰
                    for (Review r : reviewChk) {
                        ReviewListDto reviewListDto = new ReviewListDto();
                        reviewListDto.setReview_id(r.getReview_id());
                        reviewListDto.setProduct_id(r.getProduct().getId());
                        reviewListDto.setProduct_name(r.getProduct().getName());
                        reviewListDto.setTitle(r.getTitle());
                        reviewListDto.setContent(r.getContent());
                        reviewListDto.setHelp(r.getHelp());
                        reviewListDto.setRegdate(r.getRegdate());
                        list.add(reviewListDto);
                    } // end for
                }
            } // end for

        } // end for

        return list;
    }

    // 리뷰작성
    public boolean create(Long pId, ReviewDto review) {
        Member member = memberRepository.findById(1L).orElseThrow(
                ResourceNotFoundException::new
        );
//        Member member = memberRepository.findById(review.getMember_id()).orElseThrow(
//                ResourceNotFoundException::new
//        );

        Product product = productRepository.findById(pId).orElseThrow(
                ResourceNotFoundException::new
        );

        if (conditionsChk(review.getProduct_id())) {
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
            Order_Sheet_Products order_sheet_products = new Order_Sheet_Products();
            order_sheet_products.review_status();
        } else {
            return false;
        }
        return true;
    }
}