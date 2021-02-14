package com.kurlabo.backend.service;

import com.kurlabo.backend.converter.StringRevisor;
import com.kurlabo.backend.dto.order.OrderFormRequestDto;
import com.kurlabo.backend.dto.order.OrderFormResponseDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.CartRepository;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final DeliverAddressRepository deliverAddressRepository;

    @Transactional
    public OrderFormResponseDto setOrderSheet(OrderFormRequestDto orderFormRequestDto){
        Member member = memberRepository.findById(orderFormRequestDto.getMember_id()).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        List<Cart> cartList = new ArrayList<>();
        for (Long productList : orderFormRequestDto.getSelected_cart_id()){
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
            productDataList.add(sr.StringRevise(product.getData()));
            System.out.println("data >>>>>>>>>>>>>> " + sr.StringRevise(product.getData()));
            productCntList.add(list.getCnt());
        }

        List<Deliver_Address> daList = deliverAddressRepository.findByMember(member);
        Deliver_Address da = new Deliver_Address();
        for (Deliver_Address list : daList){
            if(list.getIs_main() == 1)
                da = list;
        }

        OrderFormResponseDto orderFormResponseDto = new OrderFormResponseDto(
                productDataList,
                productCntList,
                member.getName(),
                member.getPhone(),
                member.getEmail(),
                da.getDeliver_address()
        );

        return orderFormResponseDto;
    }

    public void setCheckout() {

    }

}
