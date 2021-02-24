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
                    list.getCnt(),
                    (product.getOriginal_price()-product.getDiscounted_price())
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
    public DeleteCartResponseDto deleteCart(Member member, List<Long> product_id) {
        List<Cart> deleteLists = new ArrayList<>();
        List<Long> longLists = new ArrayList<>();

        for (int i = 0; i < product_id.size(); i++){
            Cart deleteCart = cartRepository.findByMemberAndProduct_id(member, product_id.get(i));
            if(deleteCart == null){     // 만약 들어온 product_id가 Cart에 없다면 null 리턴 => 나중에 다른 예외처리로 바꿔야함
                return null;
            }
            deleteLists.add(deleteCart);
            longLists.add(product_id.get(i));
        }
        cartRepository.deleteAll(deleteLists);

        return new DeleteCartResponseDto(longLists);
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
                    cart.getCnt(),
                    (product.getOriginal_price()-product.getDiscounted_price())
            );
        } else {
            return null;
            // Exception 만들어야함
        }
    }
}
