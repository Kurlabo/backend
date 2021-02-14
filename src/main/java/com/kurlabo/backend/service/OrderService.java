package com.kurlabo.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.converter.StringRevisor;
import com.kurlabo.backend.dto.order.CheckoutRequestDto;
import com.kurlabo.backend.dto.order.OrderSheetRequestDto;
import com.kurlabo.backend.dto.order.OrderSheetResponseDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final DeliverAddressRepository deliverAddressRepository;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    public OrderSheetResponseDto getOrderSheet(OrderSheetRequestDto orderSheetRequestDto){
        Member member = memberRepository.findById(orderSheetRequestDto.getMember_id()).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        List<Cart> cartList = new ArrayList<>();
        for (Long productList : orderSheetRequestDto.getSelected_cart_id()){
            cartList.add(cartRepository.findById(productList).orElseThrow(
                    () -> new ResourceNotFoundException()
            ));
        }
        List<String> productDataList = new ArrayList<>();
        List<Integer> productCntList = new ArrayList<>();
        StringRevisor sr = new StringRevisor();
        for(Cart list : cartList){
            Product product = productRepository.findById(list.getProduct_id()).orElseThrow(
                    () -> new ResourceNotFoundException()
            );
            productDataList.add(sr.reviseBackSlash(product.getData()));
            productCntList.add(list.getCnt());
        }

        List<Deliver_Address> daList = deliverAddressRepository.findByMember(member);
        Deliver_Address da = new Deliver_Address();
        for (Deliver_Address list : daList){
            if(list.getIs_main() == 1)
                da = list;
        }

        OrderSheetResponseDto orderSheetResponseDto = new OrderSheetResponseDto(
                productDataList,
                productCntList,
                sr.reviseDoubleQuotes(member.getName()),
                sr.reviseDoubleQuotes(member.getPhone()),
                sr.reviseDoubleQuotes(member.getEmail()),
                da.getDeliver_address()
        );

        return orderSheetResponseDto;
    }

    public String setCheckout(CheckoutRequestDto dto) {
        StringRevisor sr = new StringRevisor();
        Member mem = memberRepository.findById(dto.getMember_id()).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        String productIdListStr = "";
        String returnStr = "결제에 성공하셨습니다.";

        try{
            productIdListStr = objectMapper.writeValueAsString((dto.getProduct_id_list()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Orders orders = new Orders(
                null,
                sr.reviseDoubleQuotes(mem.getName()),
                sr.reviseDoubleQuotes(mem.getName()),
                dto.getReciever(),
                dto.getReciever_phone(),
                dto.getReciever_post(),
                dto.getReciever_place(),
                dto.getReciever_visit_method(),
                dto.getCheckout_date(),
                dto.getCheckout(),
                "배송중",
                dto.getArrived_alarm(),
                productIdListStr,
                dto.getTotal_cost(),
                mem
        );

        try {
            orderRepository.save(orders);
        } catch (Exception e){
            e.printStackTrace();
            returnStr = "결제에 실패했습니다.";
        }

        return returnStr;
    }
}
