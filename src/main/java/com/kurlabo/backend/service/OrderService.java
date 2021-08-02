package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.cart.CheckoutProductsDto;
import com.kurlabo.backend.dto.order.*;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Order_Sheet_Products;
import com.kurlabo.backend.model.Orders;
import com.kurlabo.backend.repository.*;
import com.kurlabo.backend.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final DeliverAddressRepository deliverAddressRepository;
    private final OrdersRepository ordersRepository;
    private final OrderSheetProductsRepository orderSheetProductsRepository;
    private final CartService cartService;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    public OrderSheetResponseDto getOrderSheet(String token){
        Member member = memberService.findById(tokenProvider.parseTokenToGetMemberId(token));
        List<Deliver_Address> daList = deliverAddressRepository.findByMember(member);
        Deliver_Address da = new Deliver_Address();
        for (Deliver_Address list : daList){
            if(list.getIs_main() == 1)
                da = list;
        }

        Orders readyOrder = cartService.getOrderReady();

        List<Order_Sheet_Products> productsList = orderSheetProductsRepository.findAllByOrders(readyOrder);

        List<CheckoutProductsDto> productsDtoList = new ArrayList<>();

        for(Order_Sheet_Products list: productsList){
            productsDtoList.add(new CheckoutProductsDto(
                    list.getProduct_id(),
                    list.getProduct_name(),
                    list.getProduct_price(),
                    list.getDiscounted_price(),
                    list.getProduct_cnt(),
                    list.getList_image_url(),
                    list.getReview_status()
            ));
        }

        return new OrderSheetResponseDto(
                readyOrder.getId(),
                member.getName(),
                member.getPhone(),
                member.getEmail(),
                da.getDeliver_address(),
                productsDtoList,
                readyOrder.getTotal_price(),
                readyOrder.getTotal_discount_price()
        );
    }

    @Transactional
    public String setCheckout(String token, CheckoutRequestDto dto) {
        Member member = memberService.findById(tokenProvider.parseTokenToGetMemberId(token));
        String returnStr = "CHECKOUT SUCCESS";

        Orders readyOrder = cartService.getOrderReady();

        readyOrder.setSender(member.getName());
        readyOrder.setOrderer(member.getName());
        readyOrder.setReciever(dto.getReciever());
        readyOrder.setReciever_phone(dto.getReciever_phone());
        readyOrder.setReciever_post(dto.getReciever_post());
        readyOrder.setReciever_place(dto.getReciever_place());
        readyOrder.setReciever_visit_method(dto.getReciever_visit_method());
        readyOrder.setCheckoutDate(LocalDate.now());
        readyOrder.setCheckout(dto.getCheckout());
        readyOrder.setDelivery_condition("배송 준비");
        readyOrder.setArrived_alarm(dto.getArrived_alarm());
        readyOrder.setTotal_price(dto.getTotal_price());
        readyOrder.setTotal_discount_price(dto.getTotal_discount_price());
        readyOrder.setStatus("결제완료");
        readyOrder.setMember(member);

        ordersRepository.save(readyOrder);

        return returnStr;
    }

    public Page<OrderListResponseDto> getOrderList (String token, Pageable pageable) {
        Member member = memberService.findById(tokenProvider.parseTokenToGetMemberId(token));
        List<Orders> orderList = ordersRepository.findByMemberAndStatus(member, "결제완료");
        List<OrderListResponseDto> responseList = new ArrayList<>();

        for(Orders list: orderList){
            List<Order_Sheet_Products> ospList = orderSheetProductsRepository.findAllByOrders(list);
            Order_Sheet_Products osp = ospList.get(0);

            responseList.add(new OrderListResponseDto(
                    list.getCheckoutDate(),
                    osp.getProduct_name(),
                    ospList.size() - 1,
                    osp.getList_image_url(),
                    list.getId(),
                    list.getTotal_price() - list.getTotal_discount_price(),
                    list.getDelivery_condition()
            ));
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseList.size());
        return new PageImpl<>(responseList.subList(start, end), pageable, responseList.size());
    }

    public OrderViewResponseDto getOrderView(Long order_id) {
        Orders order = ordersRepository.findById(order_id).orElseThrow(() -> new DataNotFoundException("해당 주문을 찾을 수 없습니다. Id = " + order_id));
        List<OrderProductDto> orderProducts = new ArrayList<>();
        List<Order_Sheet_Products> ospList = orderSheetProductsRepository.findAllByOrders(order);

        for(Order_Sheet_Products list: ospList){
            orderProducts.add(new OrderProductDto(
                    list.getProduct_id(),
                    list.getList_image_url(),
                    list.getProduct_name(),
                    list.getProduct_price(),
                    list.getDiscounted_price(),
                    list.getProduct_cnt()
            ));
        }

        return new OrderViewResponseDto(
                orderProducts,
                order.getDelivery_condition(),
                order.getTotal_price(),
                order.getCheckout(),
                order.getOrderer(),
                order.getSender(),
                order.getCheckoutDate(),
                order.getReciever(),
                order.getReciever_phone(),
                order.getReciever_post(),
                order.getReciever_place(),
                order.getReciever_visit_method(),
                order.getArrived_alarm()
        );
    }

    public OrderEndDto setOrderEnd(Long order_id) {
        Orders orders = ordersRepository.findById(order_id).orElseThrow(() -> new DataNotFoundException("해당 주문을 찾을 수 없습니다. Id = " + order_id));
        return new OrderEndDto(
                order_id,
                orders.getTotal_price(),
                orders.getOrderer(),
                orders.getCheckout()
        );
    }
}
