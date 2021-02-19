package com.kurlabo.backend.database;

import com.hazelcast.com.eclipsesource.json.JsonObject;
import com.kurlabo.backend.converter.StringRevisor;
import com.kurlabo.backend.dto.main.MainPageProductDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.*;
import com.kurlabo.backend.model.db.Main_src;
import com.kurlabo.backend.model.db.Slide_img;
import com.kurlabo.backend.repository.*;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    void test(){
        Random random = new Random();
        List<Product> productsList = productRepository.findByDiscount_percent();
//        List<Integer> intlist = new ArrayList<>();
//        List<MainPageProductDto> list = new ArrayList<>();
//
//        for(int i = 0; i < 8; i++){
//            int n = random.nextInt(productsList.size());
//            while (intlist.contains(n)){
//                n = random.nextInt(productsList.size());
//            }
//            intlist.add(n);
//            Product product = productsList.get(n);
//
//            list.add(new MainPageProductDto(
//                    product.getId(),
//                    product.getOriginal_image_url(),
//                    product.getSticker_image_url(),
//                    product.getName(),
//                    product.getOriginal_price(),
//                    product.getDiscounted_price(),
//                    product.getDiscount_percent()
//            ));
//        }
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + productsList.size());
    }

    @Test
    @Rollback
    void InsertBoard(){
        List<Board> lists = new ArrayList<>();
        lists.add(new Board(
                null, "취소/반품/환불요청은 어떻게 하나요?", "MarketKurly", LocalDate.of(2015,5,21), 7093,
                "주문 취소는 제품 배송 전일 오후 6시까지 고객 행복 센터(1644-1107) / 1:1 문의 게시판 / 카카오톡(마켓컬리)로 접수 부탁드립니다.\n" +
                        "- 오후 6시 이후에는 주문 취소가 불가합니다. (예약딜 포함)\n" +
                        "- 고객 행복 센터(1644-1107) 운영 종료 시간인 오후 4시 이후부터는 1:1문의 게시판 접수만 가능합니다.\n" +
                        "- 예약 상품은 배송 3~4일 전에만 주문 취소가 가능합니다.\n" +
                        "- 정확한 처리를 위해 주문번호는 필수로 입력해주세요.\n\n" +
                        "* 비회원 주문건의 경우 1:1 접수가 불가하기에, 취소 및 주분변경 관련하여\n" +
                        "배송 전일 오후 4시까지 고객 행복 센터(1644-1107)/카카오톡(마켓컬리)로 접수해주시면 확인하여 조치해드립니다."
        ));
        lists.add(new Board(
                null, "시스템 이전(1/12)에 따른 비밀번호 일괄 변경에 관한 공지", "MarketKurly", LocalDate.of(2016,1,12), 6093,
                "컬리 고객님, 안녕하세요.\n\n" +
                        "1/12일 전국 배송이 시작하게 됨에 따라 사용하고 있던 시스템을 이전(카페24->고도몰)하게 되었습니다.\n\n" +
                        "기존 시스템을 그대로 이용하면서 일정 부분을 변경하는 것이라면, 비번의 변경이 필요 없겠지만, 기존에 사용하던 시스템에서 완전히 체계가 다른 시스템으로 사이트가 이전을 하다보니, 기존 사이트에서의 정보를 이전하는 작업이 필요하였습니다.\n\n" +
                        "정보보호를 위해 고객님의 비번은 저장되지 않으며, 관리자라 할지라고 정보를 조회하거나 트래킹할 수 없기에 신규사이트로 정보를 이전을 하면서 부득이하게 임시번호를 부여해 드렸음을 알려드립니다.\n\n" +
                        "(관련하여 1/11~1/12일 기간에 회원가입 시 등록해 주신 휴대폰으로 관련 정보에 대해 SMS 발송 드렸습니다.)\n\n" +
                        "SMS를 수신하기 못하셨거나 추가적으로 궁금한 점이 있으실 경우,\n" +
                        "고객센터로 연락주시거나, 통화연결이 어려우신 경우 카카오톡 아이디(ID:kurly)로 문의해주세요\n" +
                        "성심껏 답변드리도록 하겠습니다.\n" +
                        "감사합니다.\n\n" +
                        "마켓컬리 일동."
        ));
        lists.add(new Board(
                null, "에코박스 도입에 따른 한시적 포장 방법 변경 공지(종료 시점 확정)", "MarketKurly", LocalDate.of(2017,4,6), 2828,
                "안녕하세요 고객님, 마켓컬리입니다.\n\n" +
                        "4/5일 도입한 에코박스의 종료 시점관련하여 안내드립니다.\n\n\n" +
                        "- 종료 시점\n\n" +
                        "~5/15 23:00까지(5/16 수령 샛별배송 주문건까지 에코박스 포장)\n\n" +
                        "* 상품 품질에 영향을 미칠 수 있을 정도로 급격한 온도변화가 있을 경우 종료 시점 이전에 포장법이 변경될 수 있습니다.\n\n\n" +
                        "종료 이후 새로운 에코박스 도입을 통해 고객님들꼐서 만족할 수 있도록 노력하는 컬리가 되도록 하겠습니다.\n\n" +
                        "맛있고 행복한 하루 되세요!\n\n" +
                        "마켓컬리 드림"
        ));
        lists.add(new Board(
                null, "주문취소마감 시간 변경 공지", "MarketKurly", LocalDate.of(2016,3,22), 7190,
                "안녕하세요 고객님,\n주문 취소 관련하여 안내말씀 드립니다.\n" +
                        "3월 23부터 주문 취소 가능시간이 오후 9시에서 오후 6시로 변경됩니다. 즉 전날 밤 11시부터 주문하신 모든 건은 오후 6시까지만 취소가 가능하며 오후 6시 이후에 주문해 주신 건의 경우 단순변심으로 인한 취소는 불가능합니다.\n" +
                        "마켓컬리는 밤 11시까지만 주문하시면 아침 7시까지 배송해드리는 국내에서 가장 빠른 배송을 선보이고 있는데요, 빠르고 정확한 배송을 위해 오후 6시부터는 입고된 상품의 포장이 시작되며 송장이 출력됩니다.\n" +
                        "송장 출력이 완료된 주문의 경우 취소 처리가 어려운 점 너그러운 양해 부탁 드립니다.\n\n" +
                        "언제나 더 좋은 서비스를 만들기 위해 노력 하겠습니다.\n\n" +
                        "감사합니다\n" +
                        "마켓컬리 드림"
        ));
        lists.add(new Board(
                null, "[공지]동절기 포장 적용 안내(11/28 수령건~)", "MarketKurly", LocalDate.of(2017,11,24), 2223,
                "고객님, 안녕하세요.\n마켓컬리는 보냉수준을 일정하게 유지하고자, 외기 온도의 변화에 따라 포장법을 변경하고 있고, 최근 외기 온도가 떨어짐에 다라 동절기 포장법을 적용하게 되어 안내드립니다.\n\n" +
                        "현행 보냉수준 유짓 일부 상품 냉해 우려가 되고, 필요 이상의 포장재&보냉재를 사용하게 됨에 따라 동절기 동안 아래와 같이 변경하고자 하오니 이용에 참고 부탁드립니다.\n\n" +
                        "# 시행일자 11/28(화)수령건\n" +
                        "# 동절기 포장 적용 시 변경사함\n" +
                        " - 아이스젤 & 드라이아이스 수량 조정\n" +
                        " - 1차 포장 일부 상품 해제\n" +
                        "   : 보냉갱화를 위해 은박파우치로 보냉포장 했던 육류 등 일부 상품은 1차 포장 해제 예정.\n" +
                        "     cf) 해산물 등 극신건 상품에 대해서는 1차 포장 유지\n\n\n\n" +
                        "좋은 품질과 서비스로 찾아뵙기 위해 항상 노력하겠습니다.\n" +
                        "추운 날씨 건강 유념하시고, 늘 행복하세요.\n\n" +
                        "감사합니다.\n" +
                        "마켓컬리 드림."
        ));
        boardRepository.saveAll(lists);
    }

    @Test
    @Rollback(value = false)
    void InsertFavo(){
        Member mem = memberRepository.findById((long)1).orElseThrow(
                () -> new ResourceNotFoundException()
        );

        List<Favorite> list = new ArrayList<>();

        list.add(new Favorite(null, (long)5, mem));
        list.add(new Favorite(null, (long)2, mem));
        list.add(new Favorite(null, (long)9, mem));
        list.add(new Favorite(null, (long)13, mem));
        list.add(new Favorite(null, (long)129, mem));
        list.add(new Favorite(null, (long)139, mem));
        list.add(new Favorite(null, (long)111, mem));

        favoriteRepository.saveAll(list);
    }

    @Test
    @Rollback(value = false)
    void InsertCart(){
        Member member = memberRepository.findById((long)2).orElseThrow(
                () -> new ResourceNotFoundException()
        );

        List<Cart> cartList = new ArrayList<>();
        Cart cart1 = new Cart(null, (long)1, 3, member);
//        Cart cart2 = new Cart(null, (long)135, 1,member);
//        Cart cart3 = new Cart(null, (long)41, 4,member);
//        Cart cart4 = new Cart(null, (long)77, 5,member);
        cartList.add(cart1);
//        cartList.add(cart2);
//        cartList.add(cart3);
//        cartList.add(cart4);
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
            Slide_img slideImg = new Slide_img(null, slideImgs[i]);
            slideImgRepository.save(slideImg);
        }
    }

    // Insert Main page / Insta Images, Landing URLs
    @Test
    @Rollback(value = false)
    void InsertInstaDb() throws Exception {
//        String[] instaUrls = {
//                "https://www.instagram.com/p/CLBkjugpKrf/",
//                "https://www.instagram.com/p/CK5HdanJLXa/",
//                "https://www.instagram.com/p/CK0Fk8Opzlc/",
//                "https://www.instagram.com/p/CKvWCaHpSAH/",
//                "https://www.instagram.com/p/CKDCsW_pjfT/",
//                "https://www.instagram.com/p/CJ-j1ViJPnX/",
//                "https://www.instagram.com/p/CJ4zQZQJE3r/",
//                "https://www.instagram.com/p/CJvS_CYpHtm/",
//                "https://www.instagram.com/p/CJsiyY1pduY/",
//                "https://www.instagram.com/p/CJqFpArpxIL/",
//                "https://www.instagram.com/p/CJaO30LJY9Y/",
//                "https://www.instagram.com/p/CJXjjMypd9O/",
//                "https://www.instagram.com/p/CJIZwSsJT8R/",
//                "https://www.instagram.com/p/CJFMu6GJYXi/",
//                "https://www.instagram.com/p/CI7MfU0JRii/",
//                "https://www.instagram.com/p/CI2TZkwpzuO/",
//                "https://www.instagram.com/p/CIws3xEJ3Gx/",
//                "https://www.instagram.com/p/CIpZrrfJVi8/",
//                "https://www.instagram.com/p/CIkZTOppuOZ/",
//                "https://www.instagram.com/p/CIesNuYJ5Fr/"
//        };
//        String[] instaImgs = {
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/146717917_1133595607155564_6927575805678431077_n.jpg?_nc_cat=104&ccb=2&_nc_sid=8ae9d6&_nc_ohc=zCFQMEbuySoAX9akslT&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=8a5341918e533ec5754bb3e715aa50d7&oe=6045B671",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145829497_429910031618977_2686700364585205332_n.jpg?_nc_cat=101&ccb=2&_nc_sid=8ae9d6&_nc_ohc=Bpgfs7v5MrEAX_dUvyg&_nc_oc=AQnLmFvpkTuc2Qnyf1A7Cv0uZ4YMmTUN28JMU7ULdiK5AxmNhEBRKa7BswfHKjaU17w&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=006682d37604404d3d4011885d0379c7&oe=6046C3D5",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145699202_173277657764348_7186325738012278752_n.jpg?_nc_cat=108&ccb=2&_nc_sid=8ae9d6&_nc_ohc=d8fk7tg98ncAX_U9TiQ&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=ccba1e1f834e7b5011064f416fc9e32c&oe=604809C0",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/145166627_690796704929009_3137692220607683881_n.jpg?_nc_cat=102&ccb=2&_nc_sid=8ae9d6&_nc_ohc=UvQUg7XRIHoAX_HhY9K&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=0ccd9a2217dd955f4076fe0ed1e0822e&oe=60494680",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/138612535_1002060843651200_8038603372875075827_n.jpg?_nc_cat=111&ccb=2&_nc_sid=8ae9d6&_nc_ohc=tWbrlgfn0scAX9oZ8MO&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=91d86f7547a942aece145a23c08e532c&oe=6047077E",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/137535263_288737882673797_5133712456374454917_n.jpg?_nc_cat=108&ccb=2&_nc_sid=8ae9d6&_nc_ohc=7STRFBqei2sAX-Oey3Z&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=f00f77796157ce2bb44a455a6e1fb74f&oe=60474984",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/137225553_774188450120787_6930718593709013243_n.jpg?_nc_cat=102&ccb=2&_nc_sid=8ae9d6&_nc_ohc=n2WzqXR6AlkAX8OEpF3&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=befe2929baec69552ae789fdbbce26d4&oe=60477494",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/136366996_192615215904205_8306368317716846058_n.jpg?_nc_cat=107&ccb=2&_nc_sid=8ae9d6&_nc_ohc=BgN4HT50PSMAX8YDeYp&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=d5aa4a91eccb796791372fd3f420bbad&oe=60488BF7",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/135353939_2979344559018503_6638205904339600026_n.jpg?_nc_cat=110&ccb=2&_nc_sid=8ae9d6&_nc_ohc=p2TNcI8ONM0AX9Igk2A&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=08e1695b8e65daf18cdc087ef98d8e34&oe=60497903",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/135855569_1106024176515262_7522184421204961221_n.jpg?_nc_cat=104&ccb=2&_nc_sid=8ae9d6&_nc_ohc=hUa9RZwY93QAX8zFO7C&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=1bbfafc93178a8ba58592fad47ea79c6&oe=60464199",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/133740249_1081468305645551_7425631588608142276_n.jpg?_nc_cat=107&ccb=2&_nc_sid=8ae9d6&_nc_ohc=-YOQbouBm0QAX8LViJh&_nc_oc=AQlY76ytp8SExlnstcCqKd0NDYXxw8ayak2X4biQeFRRnZeCQns_jkq7iAJX-tyf0Mg&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=2d84a734e9dd60bb2dd8cc907a4f0815&oe=6047FD3A",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/133794738_431775864614286_3979166985680235825_n.jpg?_nc_cat=105&ccb=2&_nc_sid=8ae9d6&_nc_ohc=rE1kYFVHLD8AX_g1k-N&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=9f871a6ed0422f1bd2fccd2cbc4b2311&oe=6045A851",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/132014374_874243333323044_334545707376363984_n.jpg?_nc_cat=111&ccb=2&_nc_sid=8ae9d6&_nc_ohc=AaWt8ViOyIsAX8nIxIg&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=997dd984dfded7412ca22d9346895399&oe=6048F835",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/131992660_3453712921407975_3537333884386915309_n.jpg?_nc_cat=101&ccb=2&_nc_sid=8ae9d6&_nc_ohc=m8cSIXVAXrkAX80m88e&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=69fab1603ebcf82440311db41de4941c&oe=6048E423",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/131300330_426265792076878_8415232806622742688_n.jpg?_nc_cat=100&ccb=2&_nc_sid=8ae9d6&_nc_ohc=MzOoT2iVgAMAX9P5TF7&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=544344186423bc27db047a34d9bf7f54&oe=60488B5C",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/131276883_834147770758904_1061547541056682891_n.jpg?_nc_cat=108&ccb=2&_nc_sid=8ae9d6&_nc_ohc=-7ifmfpLPYIAX9ymKKU&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=c2a3dac50ddab326703ca7a075732648&oe=60493655",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/130524946_388830355790279_2119235762455725959_n.jpg?_nc_cat=101&ccb=2&_nc_sid=8ae9d6&_nc_ohc=tLBwGP7cI-wAX-08P51&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=5ecf7bf4a48ff92fbfd2be608ae3a1a5&oe=6048AEDF",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/130527127_1510988522425735_7992170002968224868_n.jpg?_nc_cat=102&ccb=2&_nc_sid=8ae9d6&_nc_ohc=8bDQqJK5nSkAX9xd1AY&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=ce4f0d32d055b281fb767dc326f6dc28&oe=60497CEF",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/130220148_742452453035145_5391754046717583792_n.jpg?_nc_cat=110&ccb=2&_nc_sid=8ae9d6&_nc_ohc=kqEY5JSkFIYAX8MBqbk&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=2cdfbb55abf51f990889c67a5aa91270&oe=60482D75",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/129722677_200004505003591_6157755695462520982_n.jpg?_nc_cat=104&ccb=2&_nc_sid=8ae9d6&_nc_ohc=JMNs8DGQN1AAX_D8sRH&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=331c6a336219a11cbd0f0c771694f51c&oe=6047356C"
//        };
////        Main_src mainSrc = new Main_src(null, "//img-cf.kurly.com/shop/data/main/15/pc_img_1568875999.png");
////        mainSrcRepository.save(mainSrc);
//
//        Main_src mainSrc = mainSrcRepository.findById((long)1).orElseThrow(
//                () -> new ResourceNotFoundException()
//        );
//        System.out.println(mainSrc);
//        for(int i = 0; i < instaUrls.length; i++){
//            Insta_src instaSrc = new Insta_src(null, instaUrls[i], instaImgs[i], mainSrc);
//            instaSrcRepository.save(instaSrc);
//        }
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

//        JsonObject str = testProduct.getData();
//        StringRevisor sr = new StringRevisor();
//        System.out.println("StringRevisor >>> " + sr.reviseBackSlash(str));
    }

    @Test
    @Rollback
    void insertReview() throws Exception {
        Member mem = new Member();
        mem.setId(1L);

        Product pro =  new Product();
        pro.setId(319L);

        Review review = new Review(
                null,
                "후기제목",
                "후기내용",
                "작성자",
                LocalDate.now(),
                3L,
                10L,
                pro,
                mem
        );

        reviewRepository.save(review);
        //verify(reviewRepository, times(1)).save(any(Review.class));
    }

}
