package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.MessageResponseDto;
import com.kurlabo.backend.dto.cart.*;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.CartRepository;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.security.jwt.TokenProvider;
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
    private final DeliverAddressRepository deliverAddressRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public GetCartResponseDto getCartList(String token){
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원을 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        Deliver_Address deliverAddress = deliverAddressRepository.findByMemberAndChecked(member, 1).orElseThrow(() ->
                new DataNotFoundException("해당 주소를 찾을 수 없습니다."));
        List<Cart> cartList = cartRepository.findByMember(member);
        List<CartProductDto> dtoLists = new ArrayList<>();

        for(Cart list : cartList){
            Product product = productRepository.findById(list.getProduct_id()).orElseThrow(() ->
                    new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + list.getProduct_id()));

            dtoLists.add(list.toCartProductDto(product));
        }

        return GetCartResponseDto.builder()
                .cartProductDto(dtoLists)
                .address(deliverAddress.getDeliver_address())
                .build();
    }

    @Transactional
    public MessageResponseDto insertCart(String token, InsertCartRequestDto dto){
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));

        for (InsertCartDto lists: dto.getInsertCartList()){
            Cart cart = cartRepository.findByMemberAndProduct_id(member, lists.getProduct_id()).orElse(Cart.builder()
                    .id(null)
                    .product_id(lists.getProduct_id())
                    .cnt(0)
                    .member(member)
                    .build());
            cart.addCnt(cart.getCnt() + lists.getCnt());
            cartRepository.save(cart);
        }

        return MessageResponseDto.builder().message("SUCCESS").build();
    }

    @Transactional
    public DeleteCartResponseDto deleteCart(String token, List<Long> idList) {
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        List<Cart> deleteLists = new ArrayList<>();
        List<Long> longLists = new ArrayList<>();

        for (Long productIdList : idList) {
            Cart deleteCart = cartRepository.findByMemberAndProduct_id(member, productIdList).orElseThrow(() ->
                    new DataNotFoundException("해당 장바구니 상품을 찾을 수 없습니다. Id = " + productIdList));
            deleteLists.add(deleteCart);
            longLists.add(productIdList);
        }

        cartRepository.deleteAll(deleteLists);

        return DeleteCartResponseDto.builder()
                .deleted_product_id(longLists)
                .build();
    }

    @Transactional
    public CartProductDto updateCnt(String token, Long product_id, UpdateCartCntRequestDto dto) {
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        Cart cart = cartRepository.findByMemberAndProduct_id(member, product_id).orElseThrow(() ->
                new DataNotFoundException("해당 장바구니 상품을 찾을 수 없습니다. Id = " + product_id));
        Product product = productRepository.findById(product_id).orElseThrow(() ->
                new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + product_id));

        cart.updateCnt(dto.getVariation());

        cartRepository.save(cart);

        return CartProductDto.builder()
                .product_id(product.getId())
                .name(product.getName())
                .original_price(product.getOriginal_price())
                .discounted_price(product.getDiscounted_price())
                .packing_type_text(product.getPacking_type_text())
                .min_ea(1)
                .max_ea(99)
                .list_image_url(product.getList_image_url())
                .cnt(cart.getCnt())
                .reduced_price(product.getOriginal_price()-product.getDiscounted_price())
                .build();
    }
}