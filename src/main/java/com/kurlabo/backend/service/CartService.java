package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.cart.*;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.CartRepository;
import com.kurlabo.backend.repository.OrderRepository;
import com.kurlabo.backend.repository.OrderSheetProductsRepository;
import com.kurlabo.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final OrderRepository orderRepository;

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
    public String insertCart(Member member, InsertCartRequestDto dtos){
//        if(cnt < 1){      // 프론트쪽에서 validation 해주는지
//
//        }
        String returnStr = "failed";

        for (InsertCartDto lists: dtos.getInsertCartList()){
            Cart cart = cartRepository.findByMemberAndProduct_id(member, lists.getProduct_id());

            if(cart != null){   // 이미 있는 상품이면 cnt만 추가로 올려줌
                cart.setCnt(cart.getCnt() + lists.getCnt());
                cartRepository.save(cart);
                returnStr = "addCnt";

            } else {            // 카트에 없는 상품이면 바로 저장해 줌
                cart = new Cart(null, lists.getProduct_id(), lists.getCnt(), member);
                cartRepository.save(cart);
                returnStr = "add";
            }
        }

        return returnStr;
    }

    @Transactional
    public DeleteCartResponseDto deleteCart(Member member, List<Long> product_id) {
        List<Cart> deleteLists = new ArrayList<>();
        List<Long> longLists = new ArrayList<>();

        for (Long productIdList : product_id) {
            Cart deleteCart = cartRepository.findByMemberAndProduct_id(member, productIdList);
            if (deleteCart == null) {     // 만약 들어온 product_id가 Cart에 없다면 null 리턴 => 나중에 다른 예외처리로 바꿔야함
                return null;
            }
            deleteLists.add(deleteCart);
            longLists.add(productIdList);
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

    public List<Orders> getOrderReadyList(){
        List<Orders> list = orderRepository.findAllByStatus("결제준비");
        System.out.println("결제준비Order 가져옴");
        return list;
    }

    // 미리 결제준비였던 데이터들 결제 취소로 만듬.
    @Transactional
    public void setOrderCancel(){
        List<Orders> readyOrders = orderRepository.findAllByStatus("결제준비");

        for(Orders ro: readyOrders){
            ro.setStatus("결제취소");
        }

        orderRepository.saveAll(readyOrders);
        System.out.println("결제준비->결제취소 완료");
    }

    // Orders에 새로운 주문서 생성
    @Transactional
    public void createNewOrder(Member member){

        setOrderCancel();
        orderRepository.save(new Orders(
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
                "",
                0,
                0,
                "결제준비",
                member
        ));
        System.out.println("주문서생성");
    }

    @Transactional
    public void setPricesToOrder(Orders order, int total_price,int discount_price){
        order.setTotal_price(total_price);
        order.setTotal_discount_price(discount_price);

        orderRepository.save(order);
        System.out.println("주문서 전체금액, 할인금액 저장 완료");
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
                    readyOrder
            ));
        }
        orderSheetProductsRepository.saveAll(orderSheetProductsList);
        System.out.println("Order_Sheet_Products 저장 완료");
    }

    @Transactional
    public String setOrdersSheet(Member member, SelectedProductInfoDto dto){
        if(dto == null){
            return "failed";
        }
        System.out.println("dto Service >>>>>>>>>>>> " + dto);

        // 1. 새로은 주문서 작성
        createNewOrder(member);

        // 2. 주문서중에 "결제준비"인 주문서를 가져옴
        List<Orders> readyList = getOrderReadyList();
        if(readyList.size() <= 0){
            return "FAILED";
        }
        Orders readyOrder = readyList.get(readyList.size() - 1);



        // 3. 요청받은 장바구니의 상품 개수만큼 dto를 for문으로 돌려 orderSheetProductsList에 저장함
        setOrderSheetProducts(readyOrder, dto);

        // 4. dto로 받은 전체 금액과 할인 금액 orders에 저장
        setPricesToOrder(readyOrder, dto.getTotal_price(), dto.getTotal_discounted_price());

        return "success";
    }

}