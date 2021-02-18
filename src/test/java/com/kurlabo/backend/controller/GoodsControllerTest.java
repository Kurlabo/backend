package com.kurlabo.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.dto.cart.InsertCartDto;
import com.kurlabo.backend.dto.cart.UpdateCartCntRequestDto;
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

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class GoodsControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void getProduct() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/1"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.product_id").value(1L))
                .andExpect(jsonPath("$.name").value("[KF365] 감자 1kg"))
                .andExpect(jsonPath("$.short_description").value("믿고 먹을 수 있는 상품을 합리적인 가격에, KF365"))
                .andExpect(jsonPath("$.original_price").value(2500))
                .andExpect(jsonPath("$.discounted_price").value(2500))
                .andExpect(jsonPath("$.detail_image_url").value("https://img-cf.kurly.com/shop/data/goods/153017237655m0.jpg"))
                .andExpect(jsonPath("$.original_image_url").value("https://img-cf.kurly.com/shop/data/goods/1530172373295l0.jpg"))
                .andExpect(jsonPath("$.productDetailDto.productDetailId").value(1L))
                .andExpect(jsonPath("$.productDetailDto.detailImgUrl").value("//img-cf.kurly.com/shop/data/goodsview/20180628/gv40000026292_1.jpg"))
                .andExpect(jsonPath("$.productDetailDto.detailTitle").value("\\n포슬포슬하고 고소한 맛\\n감자 1kg\\n"))
                .andExpect(jsonPath("$.productDetailDto.detailContext").value("간단히 쪄 먹기도 좋고, 다양한 요리와 함께 곁들여 먹기도 좋은 감자는 우리 식탁에 빼놓을 수 없는 식재료지요. 탄수화물은 물론이고 단백질, 비타민C까지 풍부해 마치 곡류와 채소를 동시에 먹은 것과 같은 효과를 줍니다. 컬리는 그때그때 유명산지 감자를 가락시장에서 수급하여 보내드립니다. 포슬포슬한 식감에 고소하고 은은한 단맛이 나 볶음, 구이, 튀김 등 다양하게 요리해서 먹을 수 있어요. 매일 식탁에 올려도 질리지 않는 감자를 컬리에서 간편하게 만나보세요."))
                .andExpect(jsonPath("$.productDetailDto.productImgUrl").value("//img-cf.kurly.com/shop/data/goodsview/20210202/gv10000156055_1.jpg"));
    }

    @DisplayName("GetCartList")
    @Test
    void getCartList() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/goods/goods_cart"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.cartDataDto[4].product_id").value((long)69))
                .andExpect(jsonPath("$.cartDataDto[4].cnt").value(6))
//                .andExpect(jsonPath("$.cartDataDto[4].data").value("{\"original_price\":7200,\"list_image_url\":\"https://img-cf.kurly.com/shop/data/goods/152646445497s0.jpg\",\"is_suggested_retail_price\":false,\"effective_date_start\":\"\",\"delivery_time_types\":[0,1],\"not_sales_text\":\"\",\"is_buy_now\":false,\"discount_start_timestamp\":0,\"discount_level\":\"\",\"review_count\":8339,\"expiration_date\":\"\",\"packing_type_text\":\"냉장/종이포장\",\"package_type\":0,\"discount_rate\":0,\"expected_point_ratio\":0,\"is_star_delivery\":false,\"contactant\":\"\",\"guides\":[\"20년산 햅쌀입니다.\",\"식품 특성상 중량은 3%내외의 차이가 발생할 수 있습니다.\"],\"event_type\":0,\"delivery_type\":0,\"main_image_url\":\"https://img-cf.kurly.com/shop/data/goods/1526464454125i0.jpg\",\"discount_start_datetime\":\"\",\"is_package_sold_out\":false,\"weight\":\"\",\"tags\":{\"types\":[],\"names\":[]},\"brand_title\":\"\",\"is_detail_sold_out\":false,\"extended_infos\":[],\"today_brix\":\"\",\"name\":\"[조선마켓] 조선향미 현미 1kg\",\"sales_unit\":1,\"discount_end_datetime\":\"\",\"delivery_price_text\":\"0원 이상 무료배송\",\"sold_out_text\":\"\",\"discount_percent\":0,\"delivery_time_type_text\":\"샛별배송/택배배송\",\"delivery_price\":0,\"use_stocked_notify\":true,\"no\":\"25578\",\"short_description\":\"조선향미의 구수한향을 간직한 현미 20년산 햅쌀(1봉/1kg) \",\"expected_point\":0,\"sticker_image_url\":null,\"origin\":\"국산\",\"is_reserve_delivery\":false,\"mobile_list_image_url\":\"\",\"discounted_price\":7200,\"sales_status\":\"ing\",\"is_sales\":true,\"unit_text\":\"\",\"delivery_area\":\"\",\"min_ea\":1,\"is_shown\":true,\"buyable_kind\":1,\"delivery_method\":\"\",\"is_kurly_pass_product\":false,\"delivery_type_text\":\"배송비\",\"max_ea\":999,\"is_expected_point\":true,\"is_divide_check\":false,\"original_image_url\":\"https://img-cf.kurly.com/shop/data/goods/1526464451559l0.jpg\",\"mobile_detail_image_url\":\"https://img-cf.kurly.com/shop/data/goods/1526464451499y0.jpg\",\"is_package\":false,\"user_event_coupon\":null,\"package_products\":[],\"is_purchase_status\":true,\"sticker\":null,\"detail_image_url\":\"https://img-cf.kurly.com/shop/data/goods/1526464454529m0.jpg\",\"long_description\":\"\",\"under_specific_quantity\":0,\"use_discount_percent\":false,\"effective_date_end\":\"\",\"discount_end_timestamp\":0,\"is_sold_out\":false}"))
                .andExpect(jsonPath("$.address").value("서울시 강동구 고덕동 삼성아파트 111동 111호"));
    }

    @DisplayName("InsertCart")
    @Test
    void insertAndUpdateCart() throws Exception {
        String content = objectMapper.writeValueAsString(new InsertCartDto((long)13, 9));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/goods_cart")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("DeleteCart")
    @Test
    void deleteCart() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/goods/goods_cart/delete")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString((long)83)))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("UpdateCartCnt")
    @Test
    void updateCartCnt() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/goods/goods_cart/13")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(
                        new UpdateCartCntRequestDto(-1)
                )))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
