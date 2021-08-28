package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.review.ReviewDto;
import com.kurlabo.backend.dto.review.WritableReviewListDto;
import com.kurlabo.backend.exception.DataNotFoundException;
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

//    // status 가 0이면 작성 가능 후기 리스트 찾기 / status 가 1이면 작성 완료 후기 리스트 찾기
//    public List<WritableReviewListDto> reviewList(Long memberId, ReviewStatus status) {
//        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));
//
//        List<WritableReviewListDto> reviewList = new ArrayList<>();
//        List<Orders> orderList = ordersRepository.findByMember(member);
//
//        // 회원이 주문한 주문내역들 반복문으로 검색
//        for (Orders order : orderList) {
//            List<Order_Sheet_Products> orderedProductsList = orderSheetProductsRepository.findAllByOrders(order);
//
//            // 주문상품들별 리뷰 작성했는지 검사
//            for (Order_Sheet_Products orderedProduct : orderedProductsList) {
//                // 주문상품 불러오기
//                Product products = productRepository.findById(orderedProduct.getProduct_id()).orElseThrow(() ->
//                        new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + orderedProduct.getProduct_id()));
//                // 주문상품에서 회원이 작성한 리뷰 불러오기
//                List<Review> writtenReviewList = reviewRepository.findByMemberAndProduct(member, products);
//
//                if (status == ReviewStatus.WRITABLE && orderedProduct.getReview_status() == 0) { // 작성가능 리뷰
//                    WritableReviewListDto writableReviewListDto = WritableReviewListDto.builder()
//                            .order_id(orderedProduct.getOrders().getId())
//                            .product_id(orderedProduct.getProduct_id())
//                            .product_name(orderedProduct.getProduct_name())
//                            .delivery_condition(orderedProduct.getOrders().getDelivery_condition())
//                            .cnt(orderedProduct.getProduct_cnt())
//                            .list_img_url(orderedProduct.getList_image_url())
//                            .written(reviewConditionsCheck(member, products.getId()))
//                            .build();
//                    reviewList.add(writableReviewListDto);
//                } else if (status == ReviewStatus.WRITTEN && orderedProduct.getReview_status() == 1) { // 작성완료 리뷰
//                    for (Review list : writtenReviewList) {
//                        WritableReviewListDto writableReviewListDto = WritableReviewListDto.builder()
//                                .product_id(list.getProduct().getId())
//                                .review_id(list.getReview_id())
//                                .product_name(list.getProduct().getName())
//                                .title(list.getTitle())
//                                .content(list.getContent())
//                                .help(list.getHelp())
//                                .regdate(list.getRegdate())
//                                .build();
//                        reviewList.add(writableReviewListDto);
//                    }
//                }
//            }
//        }
//        return reviewList;
//    }

    public Page<WritableReviewListDto> reviewBeforeList(Long memberId, Pageable pageable){
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));
        List<Orders> ordersList = ordersRepository.findByMember(member);
        List<WritableReviewListDto> responseDtoList = new ArrayList<>();

        for(Orders order : ordersList){
            System.out.println("지난 일 수 : " + ChronoUnit.DAYS.between(order.getCheckoutDate(), LocalDate.now()));
            if(ChronoUnit.DAYS.between(order.getCheckoutDate(), LocalDate.now()) > 30){
                continue;
            }
            List<Order_Sheet_Products> orderSheetProductsList = orderSheetProductsRepository.findAllByOrders(order);
            List<ReviewDto> reviewDtoList = new ArrayList<>();

            for(Order_Sheet_Products product : orderSheetProductsList){
                reviewDtoList.add(ReviewDto.builder()
                        .product_id(product.getProduct_id())
                        .product_name(product.getProduct_name())
                        .list_img_url(product.getList_image_url())
                        .cnt(product.getProduct_cnt())
                        .delivery_condition(order.getDelivery_condition())
                        .isWritten(product.getReview_status() == 1)
                        .build()
                );
            }
            responseDtoList.add(
                    WritableReviewListDto.builder()
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
                    .help(0)
                    .cnt(0)
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