package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.dto.review.WritableReviewListDto;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.ExistDataException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.*;
import com.kurlabo.backend.repository.dynamic.DynamicReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
    private final DynamicReviewRepository dynamicReviewRepository;

    public Page<WritableReviewListDto> reviewBeforeList(Long memberId, Pageable pageable){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));
        List<Orders> ordersList = ordersRepository.findByMember(member);
        List<WritableReviewListDto> responseDtoList = new ArrayList<>();

        for(Orders order : ordersList){
            if(ChronoUnit.DAYS.between(order.getCheckoutDate(), LocalDate.now()) > 30){
                continue;
            }
            List<Order_Sheet_Products> orderSheetProductsList = orderSheetProductsRepository.findAllByOrders(order);
            List<ReviewDto> reviewDtoList = new ArrayList<>();

            for(Order_Sheet_Products product : orderSheetProductsList){
                reviewDtoList.add(ReviewDto.builder()
                        .orderSheetProduct_id(product.getId())
                        .product_id(product.getProduct_id())
                        .product_name(product.getProduct_name())
                        .list_img_url(product.getList_image_url())
                        .cnt(product.getProduct_cnt())
                        .delivery_condition(order.getDelivery_condition())
                        .isWritten(product.getReview_status() == 1)
                        .build()
                );
            }
            responseDtoList.add(WritableReviewListDto.builder()
                    .order_id(order.getId())
                    .writableReviewList(reviewDtoList)
                    .build()
            );
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseDtoList.size());
        return new PageImpl<>(responseDtoList.subList(start, end), pageable, responseDtoList.size());
    }

    public Page<ReviewDto> reviewAfterList(Long memberId, Pageable pageable){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));
        List<ReviewDto> reviewList = dynamicReviewRepository.findByMember(member);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), reviewList.size());
        return new PageImpl<>(reviewList.subList(start, end), pageable, reviewList.size());
    }

    @Transactional
    public void createReview(Long memberId, ReviewDto reviewDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));
        Product product = productRepository.findById(reviewDto.getProduct_id()).orElseThrow(
                () -> new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + reviewDto.getProduct_id()));
        Orders orders = ordersRepository.findById(reviewDto.getOrders_id()).orElseThrow(
                () -> new DataNotFoundException("해당 주문내역을 찾을 수 없습니다. Id = " + reviewDto.getOrders_id()));
        Order_Sheet_Products orderSheetProducts = orderSheetProductsRepository.findByOrdersAndProduct_id(orders, product.getId()).orElseThrow(
                () -> new DataNotFoundException("해당 주문내역에서 주문 상품을 찾을 수 없습니다. productId = " + product.getId()));

        if(orderSheetProducts.getReview_status() == 1){
            throw new ExistDataException("해당 주문 상품의 후기가 이미 존재합니다.");
        }

        orderSheetProducts.updateReviewStatus();

        orderSheetProductsRepository.save(orderSheetProducts);
        reviewRepository.save(Review.builder()
                .title(reviewDto.getTitle())
                .content(reviewDto.getContent())
                .regDate(LocalDate.now())
                .writer(memberNameToDtoName(member))
                .cnt(orderSheetProducts.getProduct_cnt())
                .help(0)
                .member(member)
                .product(product)
                .build());
    }

    @Transactional
    public String increaseReviewCnt(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new DataNotFoundException("해당 후기를 찾을 수 없습니다. reviewId = " + reviewId));

        review.increaseCount();

        reviewRepository.save(review);

        return "Success increase Review Count";
    }

    @Transactional
    public String increaseReviewHelp(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new DataNotFoundException("해당 후기를 찾을 수 없습니다. reviewId = " + reviewId));

        review.increaseHelp();

        reviewRepository.save(review);

        return "Success increase Review Help";
    }

    private String memberNameToDtoName(Member member){
        StringBuilder sb = new StringBuilder(member.getName());
        for(int i = 1; i < member.getName().length(); i += 2){
            sb.replace(i, i + 1, "*");
        }
        return sb.toString();
    }
}