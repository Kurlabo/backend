package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.cart.CheckoutProductsDto;
import com.kurlabo.backend.dto.cart.DeleteCartRequestDto;
import com.kurlabo.backend.dto.cart.SelectedProductInfoDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class GoodsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void before(WebApplicationContext was) {
        mockMvc = MockMvcBuilders.webAppContextSetup(was)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void getProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/3"))
                .andExpect(jsonPath("$.product_id").value(3L))
                .andExpect(jsonPath("$.name").value("친환경 당근 500g"))
                .andExpect(status().isOk())
                .andDo(print());
    }

//    @DisplayName("상품후기 리뷰 리스트")
//    @Test
//    void getReview() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/1/1"))
//                .andExpect(status().isOk())
//                .andDo(print())
//                .andExpect(jsonPath("$.review_id").value((long)1));
//    }

    @DisplayName("상품후기 도움이 돼요")
    @Test
    void reviewHelpCnt() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/1/1")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString((long)1)))
//                .andExpect(status().isOk())
//                .andDo(print());

    }

    @DisplayName("GetCartList")
    @Test
    void getCartList() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/goods_cart"))
//                .andExpect(status().isOk())
//                .andDo(print());
//                .andExpect(jsonPath("$.cartDataDto[4].product_id").value((long)69))
//                .andExpect(jsonPath("$.cartDataDto[4].name").value("[바다원] 올리브유 김자반 볶음 50g"))
//                .andExpect(jsonPath("$.cartDataDto[4].original_price").value(1990))
//                .andExpect(jsonPath("$.cartDataDto[4].discounted_price").value(1990))
//                .andExpect(jsonPath("$.cartDataDto[4].packing_type_text").value("냉장/종이포장"))
//                .andExpect(jsonPath("$.cartDataDto[4].min_ea").value(1))
//                .andExpect(jsonPath("$.cartDataDto[4].max_ea").value(99))
//                .andExpect(jsonPath("$.cartDataDto[4].list_image_url").value("https://img-cf.kurly.com/shop/data/goods/1589264017102s0.jpg"))
//                .andExpect(jsonPath("$.cartDataDto[4].cnt").value(6))
//                .andExpect(jsonPath("$.address").value("서울시 강동구 고덕동 삼성아파트 111동 111호"));
    }

    @DisplayName("InsertCart")
    @Test
    void insertAndUpdateCart() throws Exception {
//        List<InsertCartDto> lists = new ArrayList<>(Arrays.asList(
////                new InsertCartDto((long)101, 3),
////                new InsertCartDto((long)250, 1),
//                new InsertCartDto((long)201, 1)
//        ));
//        String content = objectMapper.writeValueAsString(new InsertCartRequestDto(lists));
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/goods_cart")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(content))
//                .andExpect(status().isOk());
////                .andExpect(jsonPath("$").value("Increase cart cnt succeed"));
    }

    @DisplayName("DeleteCart")
    @Test
    void deleteCart() throws Exception {
//        List<Long> lists = new ArrayList<>(Arrays.asList((long)52,(long)109));
//        List<Long> lists = new ArrayList<>();
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/goods_cart/delete")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(new DeleteCartRequestDto(lists))))
//                .andExpect(status().isOk())
//                .andDo(print());
    }

    @DisplayName("UpdateCartCnt")
    @Test
    void updateCartCnt() throws Exception {
//        mockMvc.perform(MockMvcRequestBuilders.patch("/api/goods/goods_cart/25")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(objectMapper.writeValueAsString(
//                        new UpdateCartCntRequestDto(1)
//                )))
//                .andExpect(status().isOk());
    }

    @DisplayName("GoodsList")
    @Test
    void goodsList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/goods_list?category=0")
                .contentType(MediaType.APPLICATION_JSON_VALUE).param("page", String.valueOf(1)))
                .andExpect((jsonPath("$.content[0].product_id").value(21L)))
                .andExpect(status().isOk());
    }

    @DisplayName("SetOrderSheet")
    @Test
    void setOrderSheet() throws Exception {
//        List<CheckoutProductsDto> list = new ArrayList<>();
//        Product product1 = productRepository.findById((long)82).orElseThrow(ResourceNotFoundException::new);
//        int cnt = 1;
//        list.add(new CheckoutProductsDto(
//                product1.getId(),
//                product1.getName(),
//                product1.getOriginal_price()*cnt,
//                (product1.getOriginal_price() - product1.getDiscounted_price())*cnt,
//                cnt,
//                product1.getList_image_url()
//        ));
//        Product product2 = productRepository.findById((long)3).orElseThrow(ResourceNotFoundException::new);
//        cnt = 3;
//        list.add(new CheckoutProductsDto(
//                product2.getId(),
//                product2.getName(),
//                product2.getOriginal_price()*cnt,
//                (product2.getOriginal_price() - product2.getDiscounted_price())*cnt,
//                cnt,
//                product2.getList_image_url()
//        ));
//        Product product3 = productRepository.findById((long)100).orElseThrow(ResourceNotFoundException::new);
//        cnt = 1;
//        list.add(new CheckoutProductsDto(
//                product3.getId(),
//                product3.getName(),
//                product3.getOriginal_price()*cnt,
//                (product3.getOriginal_price() - product3.getDiscounted_price())*cnt,
//                cnt,
//                product3.getList_image_url()
//        ));
//        int totalp = 0;
//        int disp = 0;
//        for(CheckoutProductsDto cpd: list){
//            totalp += cpd.getProduct_price();
//            disp += cpd.getProduct_discount_price();
//        }
//        SelectedProductInfoDto dto = new SelectedProductInfoDto(
//                list,
//                totalp,
//                disp
//        );
//        System.out.println("dto >>>>>>>>>>>>>>>>> " + dto);
//        String str = objectMapper.writeValueAsString(dto);
//        System.out.println("str >>>>>>>>>>>>> " + str);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/goods_cart/orderSheet")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content(str))
//                .andExpect(status().isOk());
    }
}