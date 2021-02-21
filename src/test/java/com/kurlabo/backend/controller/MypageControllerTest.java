package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.mypage.DeleteWishListDto;
import com.kurlabo.backend.dto.mypage.InsertWishListDto;
import com.kurlabo.backend.dto.review.ReviewListDto;
import com.kurlabo.backend.repository.MemberRepository;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class MypageControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @DisplayName("GetWishList")
    @Test
    void getAllFavoriteList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/mypage/mypage_wishlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("page", String.valueOf(0)))
                .andExpect(status().isOk())
                .andExpect((jsonPath("$[0].product_id").value((long)5)))
                .andExpect((jsonPath("$[1].product_id").value((long)9)))
                .andExpect((jsonPath("$[2].product_id").value((long)13)))
                .andExpect((jsonPath("$[3].product_id").value((long)139)))
                .andExpect((jsonPath("$[4].product_id").value((long)111)));

    }

    @DisplayName("InsertWishList")
    @Test
    void insertWishlist() throws Exception {

        String content = objectMapper.writeValueAsString(new InsertWishListDto((long)5));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/mypage/mypage_wishlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk());//andExpect로 data 확인 필요
    }

    @DisplayName("DeleteWishList")
    @Test
    void deleteWishList() throws Exception {
//        List<Long> lists = new ArrayList<>(Arrays.asList((long)32, (long)33));
        List<Long> lists = new ArrayList<>();
        String content = objectMapper.writeValueAsString(new DeleteWishListDto(lists));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/mypage/mypage_wishlist")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("page", String.valueOf(0))
                .content(content))
                .andExpect(status().isOk());//andExpect로 data 확인 필요
    }

    @DisplayName("OrderListTest")
    @Test
    void orderListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/mypage/mypage_orderlist"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].checkout_date").value("2020.07.13(18시 32분)"))
                .andExpect(jsonPath("$[0].product_name").value("[코시] 호주산 펫밀크 1L"))
                .andExpect(jsonPath("$[0].order_id").value(Long.parseLong("1594632706623")))
                .andExpect(jsonPath("$[0].checkout_price").value(6300))
                .andExpect(jsonPath("$[0].deliver_condition").value("배송완료"))
                .andExpect(jsonPath("$[0].list_image_url").value("https://img-cf.kurly.com/shop/data/goods/1562303711815s0.jpg"))
                .andExpect(jsonPath("$[1].checkout_date").value("2021.01.16(20시 05분)"))
                .andExpect(jsonPath("$[1].product_name").value("[선물세트] 서울약사신협 석류즙 30포"))
                .andExpect(jsonPath("$[1].order_id").value(Long.parseLong("3842536821567")))
                .andExpect(jsonPath("$[1].checkout_price").value(15920))
                .andExpect(jsonPath("$[1].deliver_condition").value("배송중"))
                .andExpect(jsonPath("$[1].list_image_url").value("https://img-cf.kurly.com/shop/data/goods/1587357028431s0.jpg"));
    }

    @DisplayName("OrderDetailTest")
    @Test
    void orderDetailTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/mypage/mypage_orderview?ordno=1594632706623"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$.order_id").value(Long.parseLong("1594632706623")))
//                .andExpect(jsonPath("$.orderedProductsDtoList[0].product_name").value("[코시] 호주산 펫밀크 1L"))
//                .andExpect(jsonPath("$.orderedProductsDtoList[0].checkout_price").value(31500))
//                .andExpect(jsonPath("$.orderedProductsDtoList[0].cnt").value(5))
//                .andExpect(jsonPath("$.orderedProductsDtoList[0].deliver_condition").value("배송완료"))
//                .andExpect(jsonPath("$.checkout_total_price").value(61400))
//                .andExpect(jsonPath("$.checkout_method").value("신용카드"))
//                .andExpect(jsonPath("$.orderer_name").value("박상언"))
//                .andExpect(jsonPath("$.sender_name").value("박상언"))
//                .andExpect(jsonPath("$.checkout_date").value("2021-02-06 02:55:00"))
//                .andExpect(jsonPath("$.reciever_name").value("임정우"))
//                .andExpect(jsonPath("$.reciever_phone").value("010-4321-5678"))
//                .andExpect(jsonPath("$.reciever_address").value("(05123) 서울시 성동구 성동로 32 패스트캠퍼스 8층 C강의장"))
//                .andExpect(jsonPath("$.reciever_recieve_place").value("문 앞"));
    }

    @DisplayName("WishListTest")
    @Test
    void wishListTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/mypage/mypage_wishlist"))
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$[0].name").value("절단 셀러리 500g"))
//                .andExpect(jsonPath("$[0].discounted_price").value(2990))
//                .andExpect(jsonPath("$[0].list_image_url").value("https://img-cf.kurly.com/shop/data/goods/1584515163199s0.jpg"))
//                .andExpect(jsonPath("$[1].name").value("[락앤락] 숨쉬는 발효숙성 용기 세트"))
//                .andExpect(jsonPath("$[1].discounted_price").value(27965))
//                .andExpect(jsonPath("$[1].list_image_url").value("https://img-cf.kurly.com/shop/data/goods/1599797405749s0.jpg"));
    }

    @DisplayName("QnaTest")
    @Test
    void qnaTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/mypage/mypage_qna"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.inquiry_tag[0]").value("배송지연/불만"))
                .andExpect(jsonPath("$.inquiry_tag[5]").value("주문결제문의"))
                .andExpect(jsonPath("$.inquiry_tag[8]").value("교환문의"))
                .andExpect(jsonPath("$.order_id[0]").value("1945327660572"))
                .andExpect(jsonPath("$.order_id[1]").value("3484593475423"))
                .andExpect(jsonPath("$.email").value("noah@fastcampus.com"))
                .andExpect(jsonPath("$.phone").value("010-4321-5678"));
    }

    @DisplayName("writtenReviewsTest")
    @Test
    void writtenReviewsTest() throws Exception {
        List<Long> lists = new ArrayList<>();
        String content = objectMapper.writeValueAsString(new ReviewListDto());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/mypage/written-reviews")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .param("page", String.valueOf(0))
                .content(content))
                .andExpect(status().isOk());//andExpect로 data 확인 필요
    }
}