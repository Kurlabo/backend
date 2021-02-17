package com.kurlabo.backend.service;

import com.kurlabo.backend.converter.StringRevisor;
import com.kurlabo.backend.dto.goods.GetCartResponseDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.CartRepository;
import com.kurlabo.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public List<GetCartResponseDto> getCartList(Member member){
        List<GetCartResponseDto> dtoLists = new ArrayList<>();
        List<Cart> cartList = cartRepository.findByMember(member);

        if(cartList.size() == 0){  // 나중에 Exception 처리 해줘야함
            return null;
        }

        for(Cart list : cartList){
            Product product = productRepository.findById(list.getProduct_id()).orElseThrow(ResourceNotFoundException::new);
            StringRevisor sr = new StringRevisor();
            String str = product.getData();
            System.out.println("바로 가져온 data 값 >>>>>>>>>>>>>>> " + str);
            str = sr.reviseBackSlash(str);
            System.out.println("StringRevisor 돌린 값 >>>>>>>>>>>>>>> " + str);
            GetCartResponseDto dto = new GetCartResponseDto(product.getId(), str, list.getCnt());
            System.out.println("DTO.data에 저장된 data 값 >>>>>>>>>>>>>>> " + dto.getData());
            dtoLists.add(dto);
        }

        return dtoLists;
    }
}
