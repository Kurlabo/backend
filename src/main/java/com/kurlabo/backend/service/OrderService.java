package com.kurlabo.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.order.*;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Orders;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.OrderRepository;
import com.kurlabo.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final DeliverAddressRepository deliverAddressRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ObjectMapper objectMapper;

    public OrderSheetResponseDto getOrderSheet(Member member){
        List<Deliver_Address> daList = deliverAddressRepository.findByMember(member);
        Deliver_Address da = new Deliver_Address();
        for (Deliver_Address list : daList){
            if(list.getIs_main() == 1)
                da = list;
        }

        OrderSheetResponseDto orderSheetResponseDto = new OrderSheetResponseDto(
                member.getName(),
                member.getPhone(),
                member.getEmail(),
                da.getDeliver_address()
        );

        return orderSheetResponseDto;
    }

    @Transactional
    public String setCheckout(CheckoutRequestDto dto) throws JsonProcessingException {
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        Member mem = memberRepository.findById(dto.getMember_id()).orElseThrow(ResourceNotFoundException::new);
        String returnStr = "결제에 성공하셨습니다.";

        for(OrderListDto list: dto.getProduct_id_list()){
            Product product = productRepository.findById(list.getProduct_id()).orElseThrow(ResourceNotFoundException::new);
            orderProductDtos.add(new OrderProductDto(
                    list.getProduct_id(),
                    product.getName(),
                    product.getDiscounted_price() * list.getCnt(),
                    list.getCnt()
            ));
        }

        Orders orders = new Orders(
                null,
                mem.getName(),
                mem.getName(),
                dto.getReciever(),
                dto.getReciever_phone(),
                dto.getReciever_post(),
                dto.getReciever_place(),
                dto.getReciever_visit_method(),
                dto.getCheckout_date(),
                dto.getCheckout(),
                "배송중",
                dto.getArrived_alarm(),
                objectMapper.writeValueAsString(orderProductDtos),
                dto.getTotal_cost(),
                mem
        );

        orderRepository.save(orders);

        return returnStr;
    }

//    public getOrderList

    public OrderViewResponseDto getOrderView(Long order_id) throws JsonProcessingException {
        Orders order = orderRepository.findById(order_id).orElseThrow(ResourceNotFoundException::new);

        List<OrderProductDto> orderProducts = objectMapper.readValue(
                order.getProduct_id_cnt_list(),
                new TypeReference<List<OrderProductDto>>() {}
        );

        return new OrderViewResponseDto(
                orderProducts,
                order.getDelivery_condition(),
                order.getTotal_cost(),
                order.getCheckout(),
                order.getOrderer(),
                order.getSender(),
                order.getCheckout_date(),
                order.getReciever(),
                order.getReciever_phone(),
                order.getReciever_post(),
                order.getReciever_place(),
                order.getReciever_visit_method(),
                order.getArrived_alarm()
        );
    }
}
