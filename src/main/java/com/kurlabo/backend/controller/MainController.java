package com.kurlabo.backend.controller;

import com.kurlabo.backend.dto.testdto.MainTestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api")
public class MainController {

    @GetMapping("/main")
    public MainTestDto main() {
        MainTestDto dummyDto = new MainTestDto();

        List<String> thumbnail_img_list = new ArrayList<String>();
        List<String> landing_url_list = new ArrayList<String>();
        List<String> slide_img_list = new ArrayList<String>();

        landing_url_list.add("https://www.instagram.com/p/CK5HdanJLXa/");
        thumbnail_img_list.add("https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145829497_429910031618977_2686700364585205332_n.jpg?_nc_cat=101&ccb=2&_nc_sid=8ae9d6&_nc_ohc=-WkuqU3BZCYAX9V-8cf&_nc_oc=AQma5Jem99_iE8KN-5ZeuW8WTFP9Ffa0d88we6liTpfSpVtG-ZvhHI2xOFxZeupMS-Q&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=70036b1b024889031d3db2652ad738af&oe=6042CF55");
        landing_url_list.add("https://www.instagram.com/p/CK0Fk8Opzlc/");
        thumbnail_img_list.add("https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145699202_173277657764348_7186325738012278752_n.jpg?_nc_cat=108&ccb=2&_nc_sid=8ae9d6&_nc_ohc=kvM8p0QPigIAX_wV-BX&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=0186eb426034fe273cfeb9a301e10ae5&oe=60441540");
        landing_url_list.add("https://www.instagram.com/p/CKvWCaHpSAH/");
        thumbnail_img_list.add("https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145166627_690796704929009_3137692220607683881_n.jpg?_nc_cat=102&ccb=2&_nc_sid=8ae9d6&_nc_ohc=VoAoJAJn2l0AX8r3Yfu&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=524214a4400ebf9d4ffd1c3891f80af3&oe=60415D80");
        landing_url_list.add("https://www.instagram.com/p/CKDCsW_pjfT/");
        thumbnail_img_list.add("https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/138612535_1002060843651200_8038603372875075827_n.jpg?_nc_cat=111&ccb=2&_nc_sid=8ae9d6&_nc_ohc=S-4AyU1hdPAAX-oCL5f&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=1cba995fd31ff7c5c5c3fdd894f14b57&oe=604312FE");
        landing_url_list.add("https://www.instagram.com/p/CJ-j1ViJPnX/");
        thumbnail_img_list.add("https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/137535263_288737882673797_5133712456374454917_n.jpg?_nc_cat=108&ccb=2&_nc_sid=8ae9d6&_nc_ohc=43l4oYe49PsAX-xSWFV&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=7d85a45ef545dc13f735f7a426b1c431&oe=60435504");
        landing_url_list.add("https://www.instagram.com/p/CJ4zQZQJE3r/");
        thumbnail_img_list.add("https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/137225553_774188450120787_6930718593709013243_n.jpg?_nc_cat=102&ccb=2&_nc_sid=8ae9d6&_nc_ohc=2Wd6aip4nywAX9Ksqwz&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=d300a30b742cc15d54dd59f89c17a4da&oe=60438014");
        landing_url_list.add("https://www.instagram.com/p/CJvS_CYpHtm/");
        thumbnail_img_list.add("https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/136366996_192615215904205_8306368317716846058_n.jpg?_nc_cat=107&ccb=2&_nc_sid=8ae9d6&_nc_ohc=LeRGKe0RGPAAX-h_GrZ&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=fce39e8fea42261e80538fd07fcbe1c3&oe=6040A2F7");
        slide_img_list.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1612507314.jpg");
        slide_img_list.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1612094368.jpg");
        slide_img_list.add("https://img-cf.kurly.com/shop/data/main/1/pc_img_1612347518.jpg");

        dummyDto.setSetbyul_img("//img-cf.kurly.com/shop/data/main/15/pc_img_1568875999.png");
        dummyDto.setInsta_thumbnail_img_url(thumbnail_img_list);
        dummyDto.setInsta_landing_url(landing_url_list);
        dummyDto.setSlide_img(slide_img_list);
        dummyDto.setProduct_id((long) 2);
        dummyDto.setShort_description("껍질째 먹을 수 있는 친환경 흙당근 (500g 내외)");
        dummyDto.setName("친환경 당근 500g");
        dummyDto.setOriginal_price(3300);
        dummyDto.setDiscounted_price(3135);
        dummyDto.setOriginal_image_url("https://img-cf.kurly.com/shop/data/goods/1609141186826l0.jpg");
        dummyDto.setSticker_image_url("https://img-cf.kurly.com/shop/data/my_icon/icon_farming_coupon_20_percent.png");
        dummyDto.setCategory(0);

        return dummyDto;
    }
}
