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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public void insertCart(Member member, Long product_id, int cnt){
//        if(cnt < 1){      // 프론트쪽에서 validation 해주는지
//
//        }
        Cart cart = cartRepository.findByMemberAndProduct_id(member, product_id);

        if(cart != null){   // 이미 있는 상품이면 cnt만 추가로 올려줌
            cart.setCnt(cart.getCnt() + cnt);
            cartRepository.save(cart);
        } else {            // 카트에 없는 상품이면 바로 저장해 줌
            cart = new Cart(null, product_id, cnt, member);
            cartRepository.save(cart);
        }
    }

    @Transactional
    public void deleteCart(Member memer, Long product_id) {
        Cart cart = cartRepository.findByMemberAndProduct_id(memer, product_id);
        if(cart != null){
            cartRepository.delete(cart);
        } else {
            System.out.println("테스트용 >>>> 카트 검색 결과 없음");
            // Exception 만들어야함
        }

    }
}
