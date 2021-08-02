package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.cart.*;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.InvalidCartCntException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.*;
import com.kurlabo.backend.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final DeliverAddressService deliverAddressService;
    private final OrderSheetProductsRepository orderSheetProductsRepository;
    private final OrdersRepository ordersRepository;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public GetCartResponseDto getCartList(String token){
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원을 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        List<CartDataDto> dtoLists = new ArrayList<>();
        List<Cart> cartList = cartRepository.findByMember(member);

        if(cartList.size() == 0){  // 나중에 Exception 처리 해줘야함
            return null;
        }

        for(Cart list : cartList){
            Product product = productRepository.findById(list.getProduct_id()).orElseThrow(() -> new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + list.getProduct_id()));
            CartDataDto dto = CartDataDto.builder()
                    .product_id(product.getId())
                    .name(product.getName())
                    .original_price(product.getOriginal_price())
                    .discounted_price(product.getDiscounted_price())
                    .packing_type_text(product.getPacking_type_text())
                    .min_ea(1)
                    .max_ea(99)
                    .list_image_url(product.getList_image_url())
                    .cnt(list.getCnt())
                    .reduced_price(product.getOriginal_price()-product.getDiscounted_price())
                    .build();
            dtoLists.add(dto);
        }

        return new GetCartResponseDto(
                dtoLists,
                deliverAddressService.selectMainDeliverAddress(member).getDeliver_address()
        );
    }

    @Transactional
    public String insertCart(String token, InsertCartRequestDto dto){
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        String returnStr = "add";

        for (InsertCartDto lists: dto.getInsertCartList()){
            Cart cart = cartRepository.findByMemberAndProduct_id(member, lists.getProduct_id()).orElse(Cart.builder()
                    .id(null)
                    .product_id(lists.getProduct_id())
                    .cnt(0)
                    .member(member)
                    .build());

            cart.setCnt(cart.getCnt() + lists.getCnt());
            cartRepository.save(cart);
            returnStr = cart.getCnt() == lists.getCnt() ? returnStr : "addCnt";
        }

        return returnStr;
    }

    @Transactional
    public DeleteCartResponseDto deleteCart(String token, List<Long> product_id) {
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        List<Cart> deleteLists = new ArrayList<>();
        List<Long> longLists = new ArrayList<>();

        for (Long productIdList : product_id) {
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
    public CartDataDto updateCnt(String token, Long product_id, UpdateCartCntRequestDto dto) {
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        Cart cart = cartRepository.findByMemberAndProduct_id(member, product_id).orElseThrow(() ->
                new DataNotFoundException("해당 장바구니 상품을 찾을 수 없습니다. Id = " + product_id));
        Product product = productRepository.findById(product_id).orElseThrow(() ->
                new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + product_id));

        if(cart.getCnt() > 1){
            cart.setCnt(cart.getCnt() + dto.getVariation());
            cartRepository.save(cart);
        } else {
            throw new InvalidCartCntException("장바구니의 개수는 1개 미만이 될 수 없습니다.");
        }

        return CartDataDto.builder()
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

    public Orders getOrderReady(){
        List<Orders> list = ordersRepository.findAllByStatus("결제준비");
        return list.size() == 0 ? null : list.get(list.size() - 1);
    }

    // 미리 결제준비였던 데이터들 결제 취소로 만듬.
    @Transactional
    public void setOrderCancel(){
        List<Orders> readyOrders = ordersRepository.findAllByStatus("결제준비");

        for(Orders ro: readyOrders){
            ro.setStatus("결제취소");
        }

        ordersRepository.saveAll(readyOrders);
    }

    // Orders에 새로운 주문서 생성
    @Transactional
    public void createNewOrder(Member member){

        setOrderCancel();
        ordersRepository.save(new Orders(
                null,
                member.getName(),
                member.getName(),
                "",
                "",
                "",
                "",
                "",
                LocalDate.now(),
                "",
                "",
                "",
                0,
                0,
                "결제준비",
                member
        ));
    }

    @Transactional
    public void setPricesToOrder(Orders order, int total_price,int discount_price){
        order.setTotal_price(total_price);
        order.setTotal_discount_price(discount_price);

        ordersRepository.save(order);
    }

    @Transactional
    public void setOrderSheetProducts(Orders readyOrder, SelectedProductInfoDto dto){
        List<Order_Sheet_Products> orderSheetProductsList = new ArrayList<>();
        for(CheckoutProductsDto list : dto.getCheckout_Products()){
            orderSheetProductsList.add(new Order_Sheet_Products(
                    null,
                    list.getProduct_id(),
                    list.getProduct_name(),
                    list.getProduct_price(),
                    list.getProduct_discount_price(),
                    list.getProduct_cnt(),
                    list.getList_image_url(),
                    readyOrder,
                    list.getReview_status()
            ));
        }
        orderSheetProductsRepository.saveAll(orderSheetProductsList);
    }

    @Transactional
    public String setOrdersSheet(String token, SelectedProductInfoDto dto){
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        if(dto == null){
            return "failed";
        }

        // 1. 새로은 주문서 작성
        createNewOrder(member);

        // 2. 주문서중에 "결제준비"인 주문서를 가져옴
        Orders readyOrder = getOrderReady();
        if(getOrderReady() == null){
            return "FAILED";
        }

        // 3. 요청받은 장바구니의 상품 개수만큼 dto를 for문으로 돌려 orderSheetProductsList에 저장함
        setOrderSheetProducts(readyOrder, dto);

        // 4. dto로 받은 전체 금액과 할인 금액 orders에 저장
        setPricesToOrder(readyOrder, dto.getTotal_price(), dto.getTotal_discounted_price());

        return "success";
    }

}