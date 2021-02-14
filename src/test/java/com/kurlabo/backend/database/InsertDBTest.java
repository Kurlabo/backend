package com.kurlabo.backend.database;

import com.kurlabo.backend.converter.StringRevisor;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.model.db.Insta_src;
import com.kurlabo.backend.model.db.Main_src;
import com.kurlabo.backend.model.db.Slide_img;
import com.kurlabo.backend.repository.CartRepository;
import com.kurlabo.backend.repository.DeliverAddressRepository;
import com.kurlabo.backend.repository.MemberRepository;
import com.kurlabo.backend.repository.db.InsertDBRepository;
import com.kurlabo.backend.repository.db.InstaSrcRepository;
import com.kurlabo.backend.repository.db.MainSrcRepository;
import com.kurlabo.backend.repository.db.SlideImgRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
public class InsertDBTest {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://3.35.221.9:3306/kurlaboDB?serverTimezone=UTC";
    private static final String USER = "admin";
    private static final String PW = "admin";

    private MockMvc mockMvc;

    @Autowired
    private InsertDBRepository insertDBRepository;
    @Autowired
    private MainSrcRepository mainSrcRepository;
    @Autowired
    private InstaSrcRepository instaSrcRepository;
    @Autowired
    private SlideImgRepository slideImgRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private DeliverAddressRepository deliverAddressRepository;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @Rollback(value = false)
    void InsertCart(){
        Member member = memberRepository.findById((long)2).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        System.out.println(" >>> " + member);

        List<Cart> cartList = new ArrayList<>();
        Cart cart1 = new Cart(null, (long)13, 3,member);
        Cart cart2 = new Cart(null, (long)135, 1,member);
        Cart cart3 = new Cart(null, (long)41, 4,member);
        Cart cart4 = new Cart(null, (long)77, 5,member);
        cartList.add(cart1);
        cartList.add(cart2);
        cartList.add(cart3);
        cartList.add(cart4);
        cartRepository.saveAll(cartList);
    }

    @Test
    @Rollback(value = false)
    void InsertDeliverAddress(){
        Member mem = memberRepository.findById((long)2).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        Deliver_Address da = new Deliver_Address(
                null,
                "경기도 고양시 고양동 고양이파트 351번지",
                1,
                mem
        );
        deliverAddressRepository.save(da);
    }

    @Test
    @Rollback(value = false)
    void InsertMemberInfo(){
        Member member = new Member(
                null,
                "dkyang",
                "585858",
                "양동경",
                "dkyang@fastcampus.com",
                "01043215678",
                "남자",
                "19891122",
                "일반",
                0
        );
        memberRepository.save(member);
    }

    // Insert Main page / Slide Images
    @Test
    @Rollback(value = false)
    void InsertSlideImgDb(){
        String[] slideImgs ={
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1612751700.jpg",
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1612094368.jpg",
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1612347518.jpg",
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1612699410.jpg",
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1612094440.jpg",
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1612094559.jpg",
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1612094297.jpg",
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1602809211.jpg",
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1596164134.jpg",
                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1583112495.jpg"
        };

        Main_src mainSrc = mainSrcRepository.findById((long)1).orElseThrow(
                () -> new ResourceNotFoundException()
        );

        for(int i = 0; i < slideImgs.length; i++){
            Slide_img slideImg = new Slide_img(null, slideImgs[i], mainSrc);
            slideImgRepository.save(slideImg);
        }
    }

    // Insert Main page / Insta Images, Landing URLs
    @Test
    @Rollback(value = false)
    void InsertInstaDb() throws Exception {
        String[] instaUrls = {
                "https://www.instagram.com/p/CLBkjugpKrf/",
                "https://www.instagram.com/p/CK5HdanJLXa/",
                "https://www.instagram.com/p/CK0Fk8Opzlc/",
                "https://www.instagram.com/p/CKvWCaHpSAH/",
                "https://www.instagram.com/p/CKDCsW_pjfT/",
                "https://www.instagram.com/p/CJ-j1ViJPnX/",
                "https://www.instagram.com/p/CJ4zQZQJE3r/",
                "https://www.instagram.com/p/CJvS_CYpHtm/",
                "https://www.instagram.com/p/CJsiyY1pduY/",
                "https://www.instagram.com/p/CJqFpArpxIL/",
                "https://www.instagram.com/p/CJaO30LJY9Y/",
                "https://www.instagram.com/p/CJXjjMypd9O/",
                "https://www.instagram.com/p/CJIZwSsJT8R/",
                "https://www.instagram.com/p/CJFMu6GJYXi/",
                "https://www.instagram.com/p/CI7MfU0JRii/",
                "https://www.instagram.com/p/CI2TZkwpzuO/",
                "https://www.instagram.com/p/CIws3xEJ3Gx/",
                "https://www.instagram.com/p/CIpZrrfJVi8/",
                "https://www.instagram.com/p/CIkZTOppuOZ/",
                "https://www.instagram.com/p/CIesNuYJ5Fr/"
        };
        String[] instaImgs = {
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/146717917_1133595607155564_6927575805678431077_n.jpg?_nc_cat=104&ccb=2&_nc_sid=8ae9d6&_nc_ohc=zCFQMEbuySoAX9akslT&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=8a5341918e533ec5754bb3e715aa50d7&oe=6045B671",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145829497_429910031618977_2686700364585205332_n.jpg?_nc_cat=101&ccb=2&_nc_sid=8ae9d6&_nc_ohc=Bpgfs7v5MrEAX_dUvyg&_nc_oc=AQnLmFvpkTuc2Qnyf1A7Cv0uZ4YMmTUN28JMU7ULdiK5AxmNhEBRKa7BswfHKjaU17w&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=006682d37604404d3d4011885d0379c7&oe=6046C3D5",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145699202_173277657764348_7186325738012278752_n.jpg?_nc_cat=108&ccb=2&_nc_sid=8ae9d6&_nc_ohc=d8fk7tg98ncAX_U9TiQ&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=ccba1e1f834e7b5011064f416fc9e32c&oe=604809C0",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145166627_690796704929009_3137692220607683881_n.jpg?_nc_cat=102&ccb=2&_nc_sid=8ae9d6&_nc_ohc=UvQUg7XRIHoAX_HhY9K&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=0ccd9a2217dd955f4076fe0ed1e0822e&oe=60494680",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/138612535_1002060843651200_8038603372875075827_n.jpg?_nc_cat=111&ccb=2&_nc_sid=8ae9d6&_nc_ohc=tWbrlgfn0scAX9oZ8MO&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=91d86f7547a942aece145a23c08e532c&oe=6047077E",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/137535263_288737882673797_5133712456374454917_n.jpg?_nc_cat=108&ccb=2&_nc_sid=8ae9d6&_nc_ohc=7STRFBqei2sAX-Oey3Z&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=f00f77796157ce2bb44a455a6e1fb74f&oe=60474984",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/137225553_774188450120787_6930718593709013243_n.jpg?_nc_cat=102&ccb=2&_nc_sid=8ae9d6&_nc_ohc=n2WzqXR6AlkAX8OEpF3&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=befe2929baec69552ae789fdbbce26d4&oe=60477494",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/136366996_192615215904205_8306368317716846058_n.jpg?_nc_cat=107&ccb=2&_nc_sid=8ae9d6&_nc_ohc=BgN4HT50PSMAX8YDeYp&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=d5aa4a91eccb796791372fd3f420bbad&oe=60488BF7",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/135353939_2979344559018503_6638205904339600026_n.jpg?_nc_cat=110&ccb=2&_nc_sid=8ae9d6&_nc_ohc=p2TNcI8ONM0AX9Igk2A&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=08e1695b8e65daf18cdc087ef98d8e34&oe=60497903",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/135855569_1106024176515262_7522184421204961221_n.jpg?_nc_cat=104&ccb=2&_nc_sid=8ae9d6&_nc_ohc=hUa9RZwY93QAX8zFO7C&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=1bbfafc93178a8ba58592fad47ea79c6&oe=60464199",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/133740249_1081468305645551_7425631588608142276_n.jpg?_nc_cat=107&ccb=2&_nc_sid=8ae9d6&_nc_ohc=-YOQbouBm0QAX8LViJh&_nc_oc=AQlY76ytp8SExlnstcCqKd0NDYXxw8ayak2X4biQeFRRnZeCQns_jkq7iAJX-tyf0Mg&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=2d84a734e9dd60bb2dd8cc907a4f0815&oe=6047FD3A",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/133794738_431775864614286_3979166985680235825_n.jpg?_nc_cat=105&ccb=2&_nc_sid=8ae9d6&_nc_ohc=rE1kYFVHLD8AX_g1k-N&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=9f871a6ed0422f1bd2fccd2cbc4b2311&oe=6045A851",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/132014374_874243333323044_334545707376363984_n.jpg?_nc_cat=111&ccb=2&_nc_sid=8ae9d6&_nc_ohc=AaWt8ViOyIsAX8nIxIg&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=997dd984dfded7412ca22d9346895399&oe=6048F835",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/131992660_3453712921407975_3537333884386915309_n.jpg?_nc_cat=101&ccb=2&_nc_sid=8ae9d6&_nc_ohc=m8cSIXVAXrkAX80m88e&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=69fab1603ebcf82440311db41de4941c&oe=6048E423",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/131300330_426265792076878_8415232806622742688_n.jpg?_nc_cat=100&ccb=2&_nc_sid=8ae9d6&_nc_ohc=MzOoT2iVgAMAX9P5TF7&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=544344186423bc27db047a34d9bf7f54&oe=60488B5C",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/131276883_834147770758904_1061547541056682891_n.jpg?_nc_cat=108&ccb=2&_nc_sid=8ae9d6&_nc_ohc=-7ifmfpLPYIAX9ymKKU&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=c2a3dac50ddab326703ca7a075732648&oe=60493655",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/130524946_388830355790279_2119235762455725959_n.jpg?_nc_cat=101&ccb=2&_nc_sid=8ae9d6&_nc_ohc=tLBwGP7cI-wAX-08P51&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=5ecf7bf4a48ff92fbfd2be608ae3a1a5&oe=6048AEDF",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/130527127_1510988522425735_7992170002968224868_n.jpg?_nc_cat=102&ccb=2&_nc_sid=8ae9d6&_nc_ohc=8bDQqJK5nSkAX9xd1AY&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=ce4f0d32d055b281fb767dc326f6dc28&oe=60497CEF",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/130220148_742452453035145_5391754046717583792_n.jpg?_nc_cat=110&ccb=2&_nc_sid=8ae9d6&_nc_ohc=kqEY5JSkFIYAX8MBqbk&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=2cdfbb55abf51f990889c67a5aa91270&oe=60482D75",
                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/129722677_200004505003591_6157755695462520982_n.jpg?_nc_cat=104&ccb=2&_nc_sid=8ae9d6&_nc_ohc=JMNs8DGQN1AAX_D8sRH&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=331c6a336219a11cbd0f0c771694f51c&oe=6047356C"
        };
//        Main_src mainSrc = new Main_src(null, "//img-cf.kurly.com/shop/data/main/15/pc_img_1568875999.png");
//        mainSrcRepository.save(mainSrc);

        Main_src mainSrc = mainSrcRepository.findById((long)1).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        System.out.println(mainSrc);
        for(int i = 0; i < instaUrls.length; i++){
            Insta_src instaSrc = new Insta_src(null, instaUrls[i], instaImgs[i], mainSrc);
            instaSrcRepository.save(instaSrc);
        }
    }

    // Insert Crawling Product Data
    @Test
    @Rollback(value = false)
    void InsertProductDb() throws Exception {

//        JSONParser jsonParser = new JSONParser();
//        JSONObject jsonObject = new JSONObject();
//
//        int categoryIdx = 0;
//        int[] categoryNumList = {
//                0, 1, 2, 3, 4, 5, 6,
//                10,11,12,13,14,15,16,
//                20,21,22,23,24,25,26,27,28,
//                30,31,32,33,34,35,36,
//                40,41,42,43,44,45,
//                50,51,52,53,54,55,56,57,
//                60,61,62,63,64,65,
//                70,71,72,73,74,
//                80,81,82,83,
//                90,91,92,93,94,95,
//                100,101,102,103,104,105,106,
//                110,111,112,113,114,115,
//                120,121,122,123,124,
//                130,131,132,133,134,135,
//                140,141,142,
//                150,151,152,153,154,155,156,157,158,
//                160,161,162,163,164,165,166};
//
//        int cnt = 0;
//        for(int i = 0; i < 321; i++){//321
//            if(categoryNumList[categoryIdx] == 132){
//                if(cnt / 2 > 0){
//                    cnt = 0;
//                    categoryIdx++;
//                }
//            } else if(categoryNumList[categoryIdx] == 166){
//                if(cnt / 1 > 0){
//                    cnt = 0;
//                    categoryIdx++;
//                }
//            } else {
//                if(cnt / 3 > 0){//124까지 3개씩
//                    cnt = 0;
//                    categoryIdx++;
//                }
//            }
//
//            FileInputStream input = new FileInputStream("C:\\Crawling_Data\\" + i + ".json");
//            InputStreamReader reader = new InputStreamReader(input, "UTF-8");
//            BufferedReader in = new BufferedReader(reader);
//            jsonObject = (JSONObject) jsonParser.parse(in);
//            Product products = new Product(null, categoryNumList[categoryIdx], 0, jsonObject.toString(),null,null,null,null);
//            insertDBRepository.save(products);
//            cnt++;
//        }
//
//
        Product testProduct = insertDBRepository.findById((long) 1).orElseThrow(
                () -> new ResourceNotFoundException()
        );
        System.out.println("product >>> " + testProduct);

        String str = testProduct.getData();
        StringRevisor sr = new StringRevisor();
        System.out.println("StringRevisor >>> " + sr.StringRevise(str));
    }

}
