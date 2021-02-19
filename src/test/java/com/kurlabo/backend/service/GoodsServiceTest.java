package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.ProductDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class GoodsServiceTest {

    @InjectMocks
    private GoodsService goodsService;

    @Mock
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품 상세 조회 테스트")
    void goodDetailTest() {
        Product product = Product.builder()
                .id(319L)
                .data("{\"original_price\":2500,\"list_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/1530172376806s0.jpg\",\"is_suggested_retail_price\":false,\"effective_date_start\":\"\",\"delivery_time_types\":[0,1],\"not_sales_text\":\"\",\"is_buy_now\":false,\"discount_start_timestamp\":0,\"discount_level\":\"\",\"review_count\":47632,\"expiration_date\":\"농산물로 별도의 유통기한은 없응나 가급적 빠르게 드시기 바랍니다.\",\"packing_type_text\":\"상온\\/종이포장\",\"package_type\":0,\"discount_rate\":0,\"expected_point_ratio\":0,\"is_star_delivery\":false,\"contactant\":\"\",\"guides\":[\"식품 특성상 중량은 3% 내외의 차이가 발생할 수 있습니다.\",\"시세에 따라 가격이 변동 될 수 있습니다.\",\"햇빛을 피해 보관해 주시기 바라며 햇빛을 받아 껍질이나 내부가 초록색으로 변한 경우 섭취하지 마시기 바랍니다.\"],\"event_type\":0,\"delivery_type\":0,\"main_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/153017237651i0.jpg\",\"discount_start_datetime\":\"\",\"is_package_sold_out\":false,\"weight\":\"1kg\",\"tags\":{\"types\":[],\"names\":[]},\"brand_title\":\"\",\"is_detail_sold_out\":false,\"extended_infos\":[],\"today_brix\":\"\",\"name\":\"[KF365] 감자 1kg\",\"sales_unit\":1,\"discount_end_datetime\":\"\",\"delivery_price_text\":\"0원 이상 무료배송\",\"sold_out_text\":\"\",\"discount_percent\":0,\"delivery_time_type_text\":\"샛별배송\\/택배배송\",\"delivery_price\":0,\"use_stocked_notify\":true,\"no\":\"26448\",\"short_description\":\"믿고 먹을 수 있는 상품을 합리적인 가격에, KF365\",\"expected_point\":0,\"sticker_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/my_icon\\/icon_farming_coupon_20_percent.png\",\"origin\":\"국내산\",\"is_reserve_delivery\":false,\"mobile_list_image_url\":\"\",\"discounted_price\":2500,\"sales_status\":\"ing\",\"is_sales\":true,\"unit_text\":\"1봉\",\"delivery_area\":\"\",\"min_ea\":1,\"is_shown\":true,\"buyable_kind\":1,\"delivery_method\":\"\",\"is_kurly_pass_product\":false,\"delivery_type_text\":\"배송비\",\"max_ea\":999,\"is_expected_point\":true,\"is_divide_check\":false,\"original_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/1530172373295l0.jpg\",\"mobile_detail_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/1530172373565y0.jpg\",\"is_package\":false,\"user_event_coupon\":null,\"package_products\":[],\"is_purchase_status\":true,\"sticker\":null,\"detail_image_url\":\"https:\\/\\/img-cf.kurly.com\\/shop\\/data\\/goods\\/153017237655m0.jpg\",\"long_description\":\"\",\"under_specific_quantity\":0,\"use_discount_percent\":false,\"effective_date_end\":\"\",\"discount_end_timestamp\":0,\"is_sold_out\":false}")
                .detail_img_url("//img-cf.kurly.com/shop/data/goodsview/20180628/gv40000026292_1.jpg")
                .detail_context("간단히 쪄 먹기도 좋고, 다양한 요리와 함께 곁들여 먹기도 좋은 감자는 우리 식탁에 빼놓을 수 없는 식재료지요. 탄수화물은 물론이고 단백질, 비타민C까지 풍부해 마치 곡류와 채소를 동시에 먹은 것과 같은 효과를 줍니다. 컬리는 그때그때 유명산지 감자를 가락시장에서 수급하여 보내드립니다. 포슬포슬한 식감에 고소하고 은은한 단맛이 나 볶음, 구이, 튀김 등 다양하게 요리해서 먹을 수 있어요. 매일 식탁에 올려도 질리지 않는 감자를 컬리에서 간편하게 만나보세요.")
                .detail_title(" 포슬포슬하고 고소한 맛 감자 1kg ")
                .product_img_url("//img-cf.kurly.com/shop/data/goodsview/20210202/gv10000156055_1.jpg")
                .build();

        when (productRepository.findById(319L))
                .thenReturn(Optional.ofNullable(product));

        ProductDto re = goodsService.goodDetail(319L);

        assertThat (re.getProduct_id().equals(319L));
    }

}