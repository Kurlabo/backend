package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.ProductDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.CartRepository;
import com.kurlabo.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public void insertCart(Member member, Long product_id, int cnt){
        if(cnt < 1){

        }
        cartRepository.save(new Cart(null, product_id, cnt, member));
    }

    @Transactional
    public ProductDto goodDetail(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        List<Product> related_product = productRepository.findByCategory(product.getCategory());

        ProductDto productDto = new ProductDto();
        productDto.setProduct_id(product.getId());
        productDto.setData(product.getData());
        productDto.setRelated_product(related_product);

        return productDto;
    }
}


/*
[Product(
    id=1, category=0,
        data={
            "original_price":2500,"list_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1530172376806s0.jpg","is_suggested_retail_price":false,"effective_date_start":"","delivery_time_types":[0,1],"not_sales_text":"","is_buy_now":false,"discount_start_timestamp":0,"discount_level":"","review_count":47632,"expiration_date":"농산물로 별도의 유통기한은 없응나 가급적 빠르게 드시기 바랍니다.","packing_type_text":"상온\/종이포장","package_type":0,"discount_rate":0,"expected_point_ratio":0,"is_star_delivery":false,"contactant":"","guides":["식품 특성상 중량은 3% 내외의 차이가 발생할 수 있습니다.","시세에 따라 가격이 변동 될 수 있습니다.","햇빛을 피해 보관해 주시기 바라며 햇빛을 받아 껍질이나 내부가 초록색으로 변한 경우 섭취하지 마시기 바랍니다."],"event_type":0,"delivery_type":0,"main_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/153017237651i0.jpg","discount_start_datetime":"","is_package_sold_out":false,"weight":"1kg","tags":{"types":[],"names":[]},"brand_title":"","is_detail_sold_out":false,"extended_infos":[],"today_brix":"","name":"[KF365] 감자 1kg","sales_unit":1,"discount_end_datetime":"","delivery_price_text":"0원 이상 무료배송","sold_out_text":"","discount_percent":0,"delivery_time_type_text":"샛별배송\/택배배송","delivery_price":0,"use_stocked_notify":true,"no":"26448","short_description":"믿고 먹을 수 있는 상품을 합리적인 가격에, KF365","expected_point":0,"sticker_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/my_icon\/icon_farming_coupon_20_percent.png","origin":"국내산","is_reserve_delivery":false,"mobile_list_image_url":"","discounted_price":2500,"sales_status":"ing","is_sales":true,"unit_text":"1봉","delivery_area":"","min_ea":1,"is_shown":true,"buyable_kind":1,"delivery_method":"","is_kurly_pass_product":false,"delivery_type_text":"배송비","max_ea":999,"is_expected_point":true,"is_divide_check":false,"original_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1530172373295l0.jpg","mobile_detail_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1530172373565y0.jpg","is_package":false,"user_event_coupon":null,"package_products":[],"is_purchase_status":true,"sticker":null,"detail_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/153017237655m0.jpg","long_description":"","under_specific_quantity":0,"use_discount_percent":false,"effective_date_end":"","discount_end_timestamp":0,"is_sold_out":false}, detail_img_url=//img-cf.kurly.com/shop/data/goodsview/20180628/gv40000026292_1.jpg, detail_context=간단히 쪄 먹기도 좋고, 다양한 요리와 함께 곁들여 먹기도 좋은 감자는 우리 식탁에 빼놓을 수 없는 식재료지요. 탄수화물은 물론이고 단백질, 비타민C까지 풍부해 마치 곡류와 채소를 동시에 먹은 것과 같은 효과를 줍니다. 컬리는 그때그때 유명산지 감자를 가락시장에서 수급하여 보내드립니다. 포슬포슬한 식감에 고소하고 은은한 단맛이 나 볶음, 구이, 튀김 등 다양하게 요리해서 먹을 수 있어요. 매일 식탁에 올려도 질리지 않는 감자를 컬리에서 간편하게 만나보세요.
            , detail_title=포슬포슬하고 고소한 맛 감자 1kg, product_img_url=//img-cf.kurly.com/shop/data/goodsview/20210202/gv10000156055_1.jpg),
Product(
    id=2, category=0,
        data={
            "original_price":1300,"list_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1583285923119s0.jpg","is_suggested_retail_price":false,"effective_date_start":"","delivery_time_types":[0,1],"not_sales_text":"","is_buy_now":false,"discount_start_timestamp":0,"discount_level":"","review_count":24880,"expiration_date":"농산물로 별도의 유통기한은 없으나 가급적 빠르게 드시기 바랍니다.","packing_type_text":"냉장\/종이포장","package_type":0,"discount_rate":0,"expected_point_ratio":0,"is_star_delivery":false,"contactant":"","guides":["무게가 아닌 갯수로 배송 드리는 상품으로, 개체에 따라 크기 차이가 있을 수 있습니다.","최소 기준 130g 이상입니다."],"event_type":0,"delivery_type":0,"main_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1583285923718i0.jpg","discount_start_datetime":"","is_package_sold_out":false,"weight":"1개","tags":{"types":[],"names":[]},"brand_title":"","is_detail_sold_out":false,"extended_infos":[],"today_brix":"","name":"한끼 당근 1개","sales_unit":1,"discount_end_datetime":"","delivery_price_text":"0원 이상 무료배송","sold_out_text":"","discount_percent":0,"delivery_time_type_text":"샛별배송\/택배배송","delivery_price":0,"use_stocked_notify":true,"no":"49246","short_description":"딱 하나만 필요할 때 한끼 당근","expected_point":0,"sticker_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/my_icon\/icon_farming_coupon_20_percent.png","origin":"국산","is_reserve_delivery":false,"mobile_list_image_url":"","discounted_price":1300,"sales_status":"ing","is_sales":true,"unit_text":"1봉","delivery_area":"","min_ea":1,"is_shown":true,"buyable_kind":1,"delivery_method":"","is_kurly_pass_product":false,"delivery_type_text":"배송비","max_ea":999,"is_expected_point":true,"is_divide_check":false,"original_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1583285919646l0.jpg","mobile_detail_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1583285919611y0.jpg","is_package":false,"user_event_coupon":null,"package_products":[],"is_purchase_status":true,"sticker":null,"detail_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/158328592310m0.jpg","long_description":"","under_specific_quantity":0,"use_discount_percent":false,"effective_date_end":"","discount_end_timestamp":0,"is_sold_out":false}, detail_img_url=//img-cf.kurly.com/shop/data/goodsview/20200304/gv40000083900_1.jpg, detail_context=아삭아삭한 식감과 은은한 단맛을 지닌 당근. 김밥이나 계란말이처럼 우리 일상을 든든하게 채워주는 음식에 꼭 들어가는 반가운 채소인데요. 주홍빛 당근 하나만 더해줘도 요리의 색감이 화사하게 피어나지요. 요리에 자주 사용하는 필수 재료지만, 한 번에 많이 구비해두면 보관하기도 힘들고 쉽게 무르곤 해요. 그래서 컬리는 당근을 딱 한끼 깔끔하게 즐길 수 있는 분량만큼 담아 보내드립니다. 번거롭게 손질할 일도 없고 남은 재료를 어떻게 처리할지 걱정할 필요도 없지요. 끼니마다 신선한 채소로 다채로운 요리를 완성해보세요.
            , detail_title=베타카로틴이 풍부한 주홍빛 채소 한끼 당근, product_img_url=//img-cf.kurly.com/shop/data/goodsview/20200304/gv00000083938_1.jpg),
Product(
    id=3, category=0,
        data={
            "original_price":3300,"list_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1609141191486s0.jpg","is_suggested_retail_price":false,"effective_date_start":"","delivery_time_types":[0,1],"not_sales_text":"","is_buy_now":false,"discount_start_timestamp":0,"discount_level":"","review_count":35716,"expiration_date":"","packing_type_text":"냉장\/종이포장","package_type":0,"discount_rate":5,"expected_point_ratio":0,"is_star_delivery":false,"contactant":"","guides":["식품 특성상 중량은 5% 내외의 차이가 발생할 수 있습니다.","해당 상품은 여러 협력업체에서 납품하고 있는 상품으로 수령 시, 상이한 협력업체의 상품을 받아보실 수 있습니다. 품질은 동일 기준으로 입고되고 있사오니 참고 부탁드립니다."],"event_type":0,"delivery_type":0,"main_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1609141191365i0.jpg","discount_start_datetime":"","is_package_sold_out":false,"weight":"500g(2~4개입)","tags":{"types":[],"names":[]},"brand_title":"","is_detail_sold_out":false,"extended_infos":[],"today_brix":"","name":"친환경 당근 500g","sales_unit":1,"discount_end_datetime":"","delivery_price_text":"0원 이상 무료배송","sold_out_text":"","discount_percent":5,"delivery_time_type_text":"샛별배송\/택배배송","delivery_price":0,"use_stocked_notify":true,"no":"70","short_description":"껍질째 먹을 수 있는 친환경 흙당근 (500g 내외)","expected_point":0,"sticker_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/my_icon\/icon_farming_coupon_20_percent.png","origin":"국내","is_reserve_delivery":false,"mobile_list_image_url":"","discounted_price":3135,"sales_status":"ing","is_sales":true,"unit_text":"1봉지","delivery_area":"","min_ea":1,"is_shown":true,"buyable_kind":1,"delivery_method":"","is_kurly_pass_product":false,"delivery_type_text":"배송비","max_ea":999,"is_expected_point":true,"is_divide_check":false,"original_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1609141186826l0.jpg","mobile_detail_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/160914118697y0.jpg","is_package":false,"user_event_coupon":null,"package_products":[],"is_purchase_status":true,"sticker":null,"detail_image_url":"https:\/\/img-cf.kurly.com\/shop\/data\/goods\/1609141191801m0.jpg","long_description":"<img src=\"\/\/img-cf.kurly.com\/shop\/data\/editor\/235c52764b846c1f4cf0.jpg\" data-src=\"Carrots_160804_01.jpg\">\r\n<img src=\"\/\/img-cf.kurly.com\/shop\/data\/editor\/7df197e0a2a97bf153a8.jpg\" data-src=\"Carrots_160804_02.jpg\">\r\n<img src=\"\/\/img-cf.kurly.com\/shop\/data\/editor\/9bdbc818419fb6b954b7.jpg\" data-src=\"Carrots_160804_03.jpg\">\r\n\r\n\r\n<img src=\"\/\/img-cf.kurly.com\/shop\/data\/goods\/box.jpg\">","under_specific_quantity":0,"use_discount_percent":true,"effective_date_end":"","discount_end_timestamp":999,"is_sold_out":false}, detail_img_url=//img-cf.kurly.com/shop/data/goodsview/20201228/gv20000146996_1.jpg, detail_context=선명한 색감으로 식탁에 산뜻한 분위기를 더해주는 채소, 아름다운 빛깔뿐만 아니라 풍부한 영양소까지 자랑하는 '당근'입니다. 비타민 A의 전구체인 베타카로틴을 다량 함유하고 있어 예로부터 웰빙 식재료로 사랑받고 있지요. 컬리는 이 좋은 당근을 깐깐하게 엄선했답니다. 농약 없이 건강하게 재배한 친환경 당근이지요. 별도의 세척 및 손질 과정 없이 수확한 상태 그대로 신선하게 전해드릴게요. 곱게 갈아서 영양 주스로, 장에 찍어 먹는 아삭아삭한 반찬으로 다양하게 활용해 보세요.
            , detail_title=베타카로틴이 풍부한 주황빛 채소 친환경 당근, product_img_url=//img-cf.kurly.com/shop/data/goodsview/20201228/gv10000146998_1.jpg)
]
 */