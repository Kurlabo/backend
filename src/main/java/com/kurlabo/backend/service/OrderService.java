package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.MessageResponseDto;
import com.kurlabo.backend.dto.cart.CheckoutProductsDto;
import com.kurlabo.backend.dto.cart.SelectedProductInfoDto;
import com.kurlabo.backend.dto.order.*;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Order_Sheet_Products;
import com.kurlabo.backend.model.Orders;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.OrderSheetProductsRepository;
import com.kurlabo.backend.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final DeliverAddressRepository deliverAddressRepository;
    private final OrdersRepository ordersRepository;
    private final OrderSheetProductsRepository orderSheetProductsRepository;
    private final MemberRepository memberRepository;

    public OrderSheetResponseDto getOrderSheet(Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));
        Deliver_Address da = deliverAddressRepository.findByMemberAndChecked(member, 1).orElseThrow(() ->
                new DataNotFoundException("해당 회원의 배송지를 찾을 수 없습니다."));
        Orders readyOrder = ordersRepository.findByStatus("결제준비").orElseThrow(() ->
                new DataNotFoundException("결제 준비중인 주문을 찾을 수 없습니다."));
        List<CheckoutProductsDto> productsDtoList = orderSheetProductsRepository.findAllByOrders(readyOrder)
                .stream().map(CheckoutProductsDto::new).collect(Collectors.toList());

        return OrderSheetResponseDto.builder()
                .orders_id(readyOrder.getId())
                .orderer_name(member.getName())
                .orderer_phone(member.getPhone())
                .orderer_email(member.getEmail())
                .orderer_address(da.getDeliver_address())
                .products_list(productsDtoList)
                .total_price(readyOrder.getTotal_price())
                .total_discounted_price(readyOrder.getTotal_discount_price())
                .build();
    }

    @Transactional
    public MessageResponseDto setOrdersSheet(Long memberId, SelectedProductInfoDto dto){
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));

        // 1. 이전에 결제 준비 상태의 데이터를 결제 취소 상태로
        setOrderCancel();

        // 2. 결제 준비 상태인 주문서 새로 만들고, 주문서중에 결제 준비인 주문서를 가져옴
        Orders readyOrder = ordersRepository.save(Orders.builder()
                .id(null)
                .sender(member.getName())
                .orderer(member.getName())
                .reciever("")
                .reciever_phone("")
                .reciever_post("")
                .reciever_place("")
                .reciever_visit_method("")
                .checkoutDate(LocalDate.now())
                .checkout("")
                .delivery_condition("")
                .arrived_alarm("")
                .total_price(dto.getTotal_price())
                .total_discount_price(dto.getTotal_discounted_price())
                .status("결제준비")
                .member(member)
                .build());

        // 3. 요청받은 장바구니의 상품 개수만큼 dto를 for문으로 돌려 orderSheetProduct를 저장함
        setOrderSheetProducts(readyOrder, dto);

        return MessageResponseDto.builder().message("SUCCESS").build();
    }

    @Transactional
    public MessageResponseDto setCheckout(Long memberId, CheckoutRequestDto dto) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));

        ordersRepository.save(Orders.builder()
                .id(null)
                .sender(member.getName())
                .orderer(member.getName())
                .reciever(dto.getReciever())
                .reciever_phone(dto.getReciever_phone())
                .reciever_post(dto.getReciever_post())
                .reciever_place(dto.getReciever_place())
                .reciever_visit_method(dto.getReciever_visit_method())
                .checkoutDate(LocalDate.now())
                .checkout(dto.getCheckout())
                .delivery_condition("배송 준비")
                .arrived_alarm(dto.getArrived_alarm())
                .total_price(dto.getTotal_price())
                .total_discount_price(dto.getTotal_discount_price())
                .status("결제완료")
                .member(member)
                .build());

        return MessageResponseDto.builder().message("CHECKOUT SUCCESS").build();
    }

    public OrderEndDto setOrderEnd(Long ordersId) {
        Orders orders = ordersRepository.findById(ordersId).orElseThrow(() -> new DataNotFoundException("해당 주문을 찾을 수 없습니다. Id = " + ordersId));
        return OrderEndDto.builder()
                .orders_id(ordersId)
                .total_price(orders.getTotal_price())
                .orderer(orders.getOrderer())
                .checkout(orders.getCheckout())
                .build();
    }

    public Page<OrderListResponseDto> getOrderList (Long memberId, Pageable pageable) {
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));
        List<Orders> orderList = ordersRepository.findByMemberAndStatus(member, "결제완료");
        List<OrderListResponseDto> responseList = new ArrayList<>();

        for(Orders list: orderList){
            List<Order_Sheet_Products> ospList = orderSheetProductsRepository.findAllByOrders(list);
            Order_Sheet_Products osp = ospList.get(0);

            responseList.add(OrderListResponseDto.builder()
                    .checkout_date(list.getCheckoutDate())
                    .product_name(osp.getProduct_name())
                    .else_product_cnt(ospList.size() - 1)
                    .list_image_url(osp.getList_image_url())
                    .order_id(list.getId())
                    .total_price(list.getTotal_price() - list.getTotal_discount_price())
                    .delivery_condition(list.getDelivery_condition())
                    .build()
            );
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseList.size());
        return new PageImpl<>(responseList.subList(start, end), pageable, responseList.size());
    }

    public OrderViewResponseDto getOrderView(Long order_id) {
        Orders order = ordersRepository.findById(order_id).orElseThrow(() -> new DataNotFoundException("해당 주문을 찾을 수 없습니다. Id = " + order_id));
        List<OrderProductDto> orderDtoList = orderSheetProductsRepository.findAllByOrders(order).stream().map(OrderProductDto::new).collect(Collectors.toList());

        return OrderViewResponseDto.builder()
                .orderProducts(orderDtoList)
                .delivery_condition(order.getDelivery_condition())
                .checkout_total_price(order.getTotal_price())
                .checkout_method(order.getCheckout())
                .orderer_name(order.getOrderer())
                .sender_name(order.getSender())
                .checkout_date(order.getCheckoutDate())
                .reciever_name(order.getReciever())
                .reciever_phone(order.getReciever_phone())
                .reciever_address(order.getReciever_post())
                .reciever_place(order.getReciever_place())
                .reciever_visit_method(order.getReciever_visit_method())
                .arrived_alarm(order.getArrived_alarm())
                .build();
    }

    // 미리 결제준비였던 데이터들 결제 취소로 만듬.
    @Transactional
    void setOrderCancel(){
        Orders readyOrder = ordersRepository.findByStatus("결제준비").orElseThrow(() ->
                new DataNotFoundException("결제 준비중인 주문을 찾을 수 없습니다."));

        readyOrder.setStatus("결제취소");

        ordersRepository.save(readyOrder);
    }

    @Transactional
    void setOrderSheetProducts(Orders readyOrder, SelectedProductInfoDto dto){
        List<Order_Sheet_Products> orderSheetProductsList = new ArrayList<>();
        for(CheckoutProductsDto list : dto.getCheckout_Products()){
            orderSheetProductsList.add(Order_Sheet_Products.builder()
                    .id(null)
                    .product_id(list.getProduct_id())
                    .product_name(list.getProduct_name())
                    .product_price(list.getProduct_price())
                    .discounted_price(list.getDiscount_price())
                    .product_cnt(list.getProduct_cnt())
                    .list_image_url(list.getList_image_url())
                    .orders(readyOrder)
                    .review_status(list.getReview_status())
                    .build()
            );
        }
        orderSheetProductsRepository.saveAll(orderSheetProductsList);
    }
}
