package com.kurlabo.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.cart.CheckoutProductsDto;
import com.kurlabo.backend.dto.order.*;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.*;
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

    private final MemberRepository memberRepository;
    private final DeliverAddressRepository deliverAddressRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderSheetProductsRepository orderSheetProductsRepository;
    private final CartService cartService;
    private final ObjectMapper objectMapper;

    public OrderSheetResponseDto getOrderSheet(Member member){
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
                    list.getList_image_url()
            ));
        }

        return new OrderSheetResponseDto(
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
    public String setCheckout(Member member, CheckoutRequestDto dto) {
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
        readyOrder.setProduct_id_cnt_list("");
        readyOrder.setTotal_price(dto.getTotal_price());
        readyOrder.setTotal_discount_price(dto.getTotal_discount_price());
        readyOrder.setStatus("결제완료");
        readyOrder.setMember(member);

        orderRepository.save(readyOrder);

        return returnStr;
    }

    public Page<OrderListResponseDto> getOrderList (Member member, Pageable pageable) {
        List<Orders> orderList = orderRepository.findByMemberAndStatus(member, "결제완료");
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

    public OrderViewResponseDto getOrderView(Long order_id) throws JsonProcessingException {
        Orders order = orderRepository.findById(order_id).orElseThrow(ResourceNotFoundException::new);

        List<OrderProductDto> orderProducts = objectMapper.readValue(
                order.getProduct_id_cnt_list(),
                new TypeReference<List<OrderProductDto>>() {}
        );

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
}
