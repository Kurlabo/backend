package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.cart.CartDataDto;
import com.kurlabo.backend.dto.cart.DeleteCartResponseDto;
import com.kurlabo.backend.dto.cart.GetCartResponseDto;
import com.kurlabo.backend.dto.cart.UpdateCartCntRequestDto;
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
    private final DeliverAddressService deliverAddressService;

    public GetCartResponseDto getCartList(Member member){
        List<CartDataDto> dtoLists = new ArrayList<>();
        List<Cart> cartList = cartRepository.findByMember(member);

        if(cartList.size() == 0){  // 나중에 Exception 처리 해줘야함
            return null;
        }

        for(Cart list : cartList){
            Product product = productRepository.findById(list.getProduct_id()).orElseThrow(ResourceNotFoundException::new);
            CartDataDto dto = new CartDataDto(
                    product.getId(),
                    product.getName(),
                    product.getOriginal_price(),
                    product.getDiscounted_price(),
                    product.getPacking_type_text(),
                    1,
                    99,
                    product.getList_image_url(),
                    list.getCnt()
            );
            dtoLists.add(dto);
        }

        return new GetCartResponseDto(
                dtoLists,
                deliverAddressService.selectMainDeliverAddress(member).getDeliver_address()
        );
    }

    @Transactional
    public String insertCart(Member member, Long product_id, int cnt){
//        if(cnt < 1){      // 프론트쪽에서 validation 해주는지
//
//        }
        Cart cart = cartRepository.findByMemberAndProduct_id(member, product_id);

        if(cart != null){   // 이미 있는 상품이면 cnt만 추가로 올려줌
            cart.setCnt(cart.getCnt() + cnt);
            cartRepository.save(cart);
            return "addCnt";

        } else {            // 카트에 없는 상품이면 바로 저장해 줌
            cart = new Cart(null, product_id, cnt, member);
            cartRepository.save(cart);
            return "add";
        }
    }

    @Transactional
    public DeleteCartResponseDto deleteCart(Member member, Long product_id) {
        Cart cart = cartRepository.findByMemberAndProduct_id(member, product_id);

        if(cart != null){
            cartRepository.delete(cart);
            return new DeleteCartResponseDto(product_id);
        } else {
            // Exception 만들어야함
            return null;
        }
    }

    @Transactional
    public CartDataDto updateCnt(Member memer, Long product_id, UpdateCartCntRequestDto dto) {
        Cart cart = cartRepository.findByMemberAndProduct_id(memer, product_id);
        Product product = productRepository.findById(product_id).orElseThrow(ResourceNotFoundException::new);
        if(cart != null){
            cart.setCnt(cart.getCnt() + dto.getVariation());
            cartRepository.save(cart);
            return new CartDataDto(
                    product.getId(),
                    product.getName(),
                    product.getOriginal_price(),
                    product.getDiscounted_price(),
                    product.getPacking_type_text(),
                    1,
                    99,
                    product.getList_image_url(),
                    cart.getCnt()
            );
        } else {
            return null;
            // Exception 만들어야함
        }
    }
}
