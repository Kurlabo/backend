package com.kurlabo.backend.controller;

import lombok.var;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class GoodsControllerTest {
    private MockMvc mockMvc;

    @BeforeEach
    void before(WebApplicationContext was) {
        mockMvc = MockMvcBuilders.webAppContextSetup(was)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void getProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/320"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.product_id").value((long)320))
                .andExpect(jsonPath("$.data").value("{\"original_price\":2500,\"list_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/1530172376806s0.jpg\",\"is_suggested_retail_price\":false,\"effective_date_start\":\"\",\"delivery_time_types\":[0,1],\"not_sales_text\":\"\",\"is_buy_now\":false,\"discount_start_timestamp\":0,\"discount_level\":\"\",\"review_count\":47632,\"expiration_date\":\"농산물로 별도의 유통기한은 없응나 가급적 빠르게 드시기 바랍니다.\",\"packing_type_text\":\"상온\\/종이포장\",\"package_type\":0,\"discount_rate\":0,\"expected_point_ratio\":0,\"is_star_delivery\":false,\"contactant\":\"\",\"guides\":[\"식품 특성상 중량은 3% 내외의 차이가 발생할 수 있습니다.\",\"시세에 따라 가격이 변동 될 수 있습니다.\",\"햇빛을 피해 보관해 주시기 바라며 햇빛을 받아 껍질이나 내부가 초록색으로 변한 경우 섭취하지 마시기 바랍니다.\"],\"event_type\":0,\"delivery_type\":0,\"main_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/153017237651i0.jpg\",\"discount_start_datetime\":\"\",\"is_package_sold_out\":false,\"weight\":\"1kg\",\"tags\":{\"types\":[],\"names\":[]},\"brand_title\":\"\",\"is_detail_sold_out\":false,\"extended_infos\":[],\"today_brix\":\"\",\"name\":\"[KF365] 감자 1kg\",\"sales_unit\":1,\"discount_end_datetime\":\"\",\"delivery_price_text\":\"0원 이상 무료배송\",\"sold_out_text\":\"\",\"discount_percent\":0,\"delivery_time_type_text\":\"샛별배송\\/택배배송\",\"delivery_price\":0,\"use_stocked_notify\":true,\"no\":\"26448\",\"short_description\":\"믿고 먹을 수 있는 상품을 합리적인 가격에, KF365\",\"expected_point\":0,\"sticker_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/my_icon\\/icon_farming_coupon_20_percent.png\",\"origin\":\"국내산\",\"is_reserve_delivery\":false,\"mobile_list_image_url\":\"\",\"discounted_price\":2500,\"sales_status\":\"ing\",\"is_sales\":true,\"unit_text\":\"1봉\",\"delivery_area\":\"\",\"min_ea\":1,\"is_shown\":true,\"buyable_kind\":1,\"delivery_method\":\"\",\"is_kurly_pass_product\":false,\"delivery_type_text\":\"배송비\",\"max_ea\":999,\"is_expected_point\":true,\"is_divide_check\":false,\"original_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/1530172373295l0.jpg\",\"mobile_detail_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/1530172373565y0.jpg\",\"is_package\":false,\"user_event_coupon\":null,\"package_products\":[],\"is_purchase_status\":true,\"sticker\":null,\"detail_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/153017237655m0.jpg\",\"long_description\":\"\",\"under_specific_quantity\":0,\"use_discount_percent\":false,\"effective_date_end\":\"\",\"discount_end_timestamp\":0,\"is_sold_out\":false}"));
    }

    @DisplayName("CartTest")
    @Test
    void cartTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/goods_cart"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.product_info_dto[0].product_id").value((long)1))
                .andExpect(jsonPath("$.product_info_dto[0].category").value(0))
                .andExpect(jsonPath("$.product_info_dto[0].name").value("한끼 당근 1개"))
                .andExpect(jsonPath("$.product_info_dto[0].short_description").value("딱 하나만 필요할 때 한끼 당근"))
                .andExpect(jsonPath("$.product_info_dto[0].original_price").value(1300))
                .andExpect(jsonPath("$.product_info_dto[0].discounted_price").value(1300))
                .andExpect(jsonPath("$.product_info_dto[0].original_image_url").value("https://img-cf.kurly.com/shop/data/goods/1583285919646l0.jpg"))
                .andExpect(jsonPath("$.product_info_dto[0].sticker_image_url").value("https://img-cf.kurly.com/shop/data/my_icon/icon_farming_coupon_20_percent.png"))
                .andExpect(jsonPath("$.product_info_dto[0].packing_type_text").value("냉장/종이포장"))
                .andExpect(jsonPath("$.user_info_dto.member_id").value((long)1))
                .andExpect(jsonPath("$.user_info_dto.uid").value("noah"))
                .andExpect(jsonPath("$.user_info_dto.address").value("서울시 성동구 아차산로 18 (뚝섬역)"));
    }


}
