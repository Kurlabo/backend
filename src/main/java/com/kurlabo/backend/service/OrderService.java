package com.kurlabo.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.converter.StringRevisor;
import com.kurlabo.backend.dto.order.CheckoutRequestDto;
import com.kurlabo.backend.dto.order.OrderSheetRequestDto;
import com.kurlabo.backend.dto.order.OrderSheetResponseDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Orders;
import com.kurlabo.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final DeliverAddressRepository deliverAddressRepository;
    private final OrderRepository orderRepository;
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
    public String setCheckout(CheckoutRequestDto dto) {
        Member mem = memberRepository.findById(dto.getMember_id()).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        String productIdCntListStr = "";
        String returnStr = "결제에 성공하셨습니다.";

        try{
            productIdCntListStr = objectMapper.writeValueAsString((dto.getProduct_id_list()));
        } catch (Exception e){
            e.printStackTrace();
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
                productIdCntListStr,
                dto.getTotal_cost(),
                mem
        );

        orderRepository.save(orders);

        return returnStr;
    }
}
