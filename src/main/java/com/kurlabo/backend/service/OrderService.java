package com.kurlabo.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.cart.CheckoutProductsDto;
import com.kurlabo.backend.dto.order.*;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final DeliverAddressRepository deliverAddressRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderSheetProductsRepository orderSheetProductsRepository;
    private final CartService cartService;
    private final ObjectMapper objectMapper;

    public OrderSheetResponseDto getOrderSheet(Member member){
        List<Deliver_Address> daList = deliverAddressRepository.findByMember(member);
        Deliver_Address da = new Deliver_Address();
        for (Deliver_Address list : daList){
            if(list.getIs_main() == 1)
                da = list;
        }

        Orders readyOrder = cartService.getOrderReady();

        List<Order_Sheet_Products> productsList = orderSheetProductsRepository.findAllByOrders(readyOrder);

        List<CheckoutProductsDto> productsDtoList = new ArrayList<>();

        for(Order_Sheet_Products list: productsList){
            productsDtoList.add(new CheckoutProductsDto(
                    list.getProduct_id(),
                    list.getProduct_name(),
                    list.getProduct_price(),
                    list.getDiscounted_price(),
                    list.getProduct_cnt(),
                    list.getList_image_url()
            ));
        }

        return new OrderSheetResponseDto(
                member.getName(),
                member.getPhone(),
                member.getEmail(),
                da.getDeliver_address(),
                productsDtoList,
                readyOrder.getTotal_price(),
                readyOrder.getTotal_discount_price()
        );
    }

    @Transactional
    public String setCheckout(CheckoutRequestDto dto) throws JsonProcessingException {
        List<OrderProductDto> orderProductDtos = new ArrayList<>();
        Member mem = memberRepository.findById(dto.getMember_id()).orElseThrow(ResourceNotFoundException::new);
        String returnStr = "결제에 성공하셨습니다.";

        for(OrderListDto list: dto.getProduct_id_list()){
            Product product = productRepository.findById(list.getProduct_id()).orElseThrow(ResourceNotFoundException::new);
            orderProductDtos.add(new OrderProductDto(
                    list.getProduct_id(),
                    product.getList_image_url(),
                    product.getName(),
                    product.getDiscounted_price() * list.getCnt(),
                    ((product.getOriginal_price()-product.getDiscounted_price()) * list.getCnt()),
                    list.getCnt()
            ));
        }

//        Orders orders = new Orders(
//                null,
//                mem.getName(),
//                mem.getName(),
//                dto.getReciever(),
//                dto.getReciever_phone(),
//                dto.getReciever_post(),
//                dto.getReciever_place(),
//                dto.getReciever_visit_method(),
//                dto.getCheckout_date(),
//                dto.getCheckout(),
//                "배송중",
//                dto.getArrived_alarm(),
//                objectMapper.writeValueAsString(orderProductDtos),
//                dto.getTotal_cost(),
//                0,
//                mem
//        );

//        orderRepository.save(orders);

        return returnStr;
    }

    @Transactional
    public void setOrderSheet(){
        List<Order_Sheet_Products> orderSheetProductsList = new ArrayList<>();



        orderSheetProductsRepository.saveAll(orderSheetProductsList);
    }

    public Page<OrderListResponseDto> getOrderList (Member member, Pageable pageable) throws JsonProcessingException {
        List<Orders> orderList = orderRepository.findByMember(member);
        List<OrderListResponseDto> responseList = new ArrayList<>();

        for(Orders list: orderList){
            List<OrderProductDto> orderProducts = objectMapper.readValue(
                    list.getProduct_id_cnt_list(),
                    new TypeReference<List<OrderProductDto>>() {}
            );

            Product mainProduct = productRepository.findById(orderProducts.get(0).getProduct_id()).orElseThrow(ResourceNotFoundException::new);

            String addProduct_name = mainProduct.getName();
            int addElse_product_cnt = orderProducts.size() - 1;
            String addList_image_url = mainProduct.getList_image_url();

//            responseList.add(new OrderListResponseDto(
//                    list.getCheckoutDate(),
//                    addProduct_name,
//                    addElse_product_cnt,
//                    addList_image_url,
//                    list.getId(),
//                    list.getTotal_cost(),
//                    list.getDelivery_condition()
//            ));
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseList.size());
        return new PageImpl<>(responseList.subList(start, end), pageable, responseList.size());
    }

    public OrderViewResponseDto getOrderView(Long order_id) throws JsonProcessingException {
        Orders order = orderRepository.findById(order_id).orElseThrow(ResourceNotFoundException::new);

        List<OrderProductDto> orderProducts = objectMapper.readValue(
                order.getProduct_id_cnt_list(),
                new TypeReference<List<OrderProductDto>>() {}
        );

        return new OrderViewResponseDto(
                orderProducts,
                order.getDelivery_condition(),
                order.getTotal_price(),
                order.getCheckout(),
                order.getOrderer(),
                order.getSender(),
                order.getCheckoutDate(),
                order.getReciever(),
                order.getReciever_phone(),
                order.getReciever_post(),
                order.getReciever_place(),
                order.getReciever_visit_method(),
                order.getArrived_alarm()
        );
    }
}
