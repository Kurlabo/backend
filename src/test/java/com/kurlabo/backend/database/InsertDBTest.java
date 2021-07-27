package com.kurlabo.backend.database;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kurlabo.backend.repository.*;
import com.kurlabo.backend.repository.db.InsertDBRepository;
import com.kurlabo.backend.repository.InstaSrcRepository;
import com.kurlabo.backend.repository.MainSrcRepository;
import com.kurlabo.backend.repository.SlideImgRepository;
import com.kurlabo.backend.service.CartService;
import com.kurlabo.backend.service.MemberService;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.io.IOException;
import java.util.ArrayList;
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
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderSheetProductsRepository orderSheetProductsRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MemberService memberService;

    @BeforeEach
    void before(WebApplicationContext wac) {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .alwaysDo(print())
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    private List<String> crawlingData (int productNo){
        String url = "https://www.kurly.com/shop/goods/goods_view.php?&goodsno=" + productNo;
        List<String> list = new ArrayList<>();

        try {
            Connection connection = Jsoup.connect(url);

            Document document = connection.get();

            Elements elements = document.getElementsByAttributeValue("id", "goods-view-infomation");

            if(elements.size() < 1){
                return null;
            }

            list.add(elements.get(0).getElementsByAttributeValue("class", "goods_intro").get(0).getElementsByTag("img").attr("src"));
            list.add(elements.get(0).getElementsByAttributeValue("class", "goods_intro").get(0).getElementsByAttributeValue("class", "words").text());
            list.add(elements.get(0).getElementsByAttributeValue("id", "goods_pi").get(0).getElementsByTag("img").attr("src"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Test
    public void test(){

    }

//    // Insert Board (v)
//    @Test
//    @Rollback
//    void InsertBoard(){
//        List<Board> lists = new ArrayList<>();
//        lists.add(new Board(
//                null, "취소/반품/환불요청은 어떻게 하나요?", "MarketKurly", LocalDate.of(2015,5,21), 7093,
//                "주문 취소는 제품 배송 전일 오후 6시까지 고객 행복 센터(1644-1107) / 1:1 문의 게시판 / 카카오톡(마켓컬리)로 접수 부탁드립니다.\n" +
//                        "- 오후 6시 이후에는 주문 취소가 불가합니다. (예약딜 포함)\n" +
//                        "- 고객 행복 센터(1644-1107) 운영 종료 시간인 오후 4시 이후부터는 1:1문의 게시판 접수만 가능합니다.\n" +
//                        "- 예약 상품은 배송 3~4일 전에만 주문 취소가 가능합니다.\n" +
//                        "- 정확한 처리를 위해 주문번호는 필수로 입력해주세요.\n\n" +
//                        "* 비회원 주문건의 경우 1:1 접수가 불가하기에, 취소 및 주분변경 관련하여\n" +
//                        "배송 전일 오후 4시까지 고객 행복 센터(1644-1107)/카카오톡(마켓컬리)로 접수해주시면 확인하여 조치해드립니다."
//        ));
//        lists.add(new Board(
//                null, "시스템 이전(1/12)에 따른 비밀번호 일괄 변경에 관한 공지", "MarketKurly", LocalDate.of(2016,1,12), 6093,
//                "컬리 고객님, 안녕하세요.\n\n" +
//                        "1/12일 전국 배송이 시작하게 됨에 따라 사용하고 있던 시스템을 이전(카페24->고도몰)하게 되었습니다.\n\n" +
//                        "기존 시스템을 그대로 이용하면서 일정 부분을 변경하는 것이라면, 비번의 변경이 필요 없겠지만, 기존에 사용하던 시스템에서 완전히 체계가 다른 시스템으로 사이트가 이전을 하다보니, 기존 사이트에서의 정보를 이전하는 작업이 필요하였습니다.\n\n" +
//                        "정보보호를 위해 고객님의 비번은 저장되지 않으며, 관리자라 할지라고 정보를 조회하거나 트래킹할 수 없기에 신규사이트로 정보를 이전을 하면서 부득이하게 임시번호를 부여해 드렸음을 알려드립니다.\n\n" +
//                        "(관련하여 1/11~1/12일 기간에 회원가입 시 등록해 주신 휴대폰으로 관련 정보에 대해 SMS 발송 드렸습니다.)\n\n" +
//                        "SMS를 수신하기 못하셨거나 추가적으로 궁금한 점이 있으실 경우,\n" +
//                        "고객센터로 연락주시거나, 통화연결이 어려우신 경우 카카오톡 아이디(ID:kurly)로 문의해주세요\n" +
//                        "성심껏 답변드리도록 하겠습니다.\n" +
//                        "감사합니다.\n\n" +
//                        "마켓컬리 일동."
//        ));
//        lists.add(new Board(
//                null, "에코박스 도입에 따른 한시적 포장 방법 변경 공지(종료 시점 확정)", "MarketKurly", LocalDate.of(2017,4,6), 2828,
//                "안녕하세요 고객님, 마켓컬리입니다.\n\n" +
//                        "4/5일 도입한 에코박스의 종료 시점관련하여 안내드립니다.\n\n\n" +
//                        "- 종료 시점\n\n" +
//                        "~5/15 23:00까지(5/16 수령 샛별배송 주문건까지 에코박스 포장)\n\n" +
//                        "* 상품 품질에 영향을 미칠 수 있을 정도로 급격한 온도변화가 있을 경우 종료 시점 이전에 포장법이 변경될 수 있습니다.\n\n\n" +
//                        "종료 이후 새로운 에코박스 도입을 통해 고객님들꼐서 만족할 수 있도록 노력하는 컬리가 되도록 하겠습니다.\n\n" +
//                        "맛있고 행복한 하루 되세요!\n\n" +
//                        "마켓컬리 드림"
//        ));
//        lists.add(new Board(
//                null, "주문취소마감 시간 변경 공지", "MarketKurly", LocalDate.of(2016,3,22), 7190,
//                "안녕하세요 고객님,\n주문 취소 관련하여 안내말씀 드립니다.\n" +
//                        "3월 23부터 주문 취소 가능시간이 오후 9시에서 오후 6시로 변경됩니다. 즉 전날 밤 11시부터 주문하신 모든 건은 오후 6시까지만 취소가 가능하며 오후 6시 이후에 주문해 주신 건의 경우 단순변심으로 인한 취소는 불가능합니다.\n" +
//                        "마켓컬리는 밤 11시까지만 주문하시면 아침 7시까지 배송해드리는 국내에서 가장 빠른 배송을 선보이고 있는데요, 빠르고 정확한 배송을 위해 오후 6시부터는 입고된 상품의 포장이 시작되며 송장이 출력됩니다.\n" +
//                        "송장 출력이 완료된 주문의 경우 취소 처리가 어려운 점 너그러운 양해 부탁 드립니다.\n\n" +
//                        "언제나 더 좋은 서비스를 만들기 위해 노력 하겠습니다.\n\n" +
//                        "감사합니다\n" +
//                        "마켓컬리 드림"
//        ));
//        lists.add(new Board(
//                null, "[공지]동절기 포장 적용 안내(11/28 수령건~)", "MarketKurly", LocalDate.of(2017,11,24), 2223,
//                "고객님, 안녕하세요.\n마켓컬리는 보냉수준을 일정하게 유지하고자, 외기 온도의 변화에 따라 포장법을 변경하고 있고, 최근 외기 온도가 떨어짐에 다라 동절기 포장법을 적용하게 되어 안내드립니다.\n\n" +
//                        "현행 보냉수준 유짓 일부 상품 냉해 우려가 되고, 필요 이상의 포장재&보냉재를 사용하게 됨에 따라 동절기 동안 아래와 같이 변경하고자 하오니 이용에 참고 부탁드립니다.\n\n" +
//                        "# 시행일자 11/28(화)수령건\n" +
//                        "# 동절기 포장 적용 시 변경사함\n" +
//                        " - 아이스젤 & 드라이아이스 수량 조정\n" +
//                        " - 1차 포장 일부 상품 해제\n" +
//                        "   : 보냉갱화를 위해 은박파우치로 보냉포장 했던 육류 등 일부 상품은 1차 포장 해제 예정.\n" +
//                        "     cf) 해산물 등 극신건 상품에 대해서는 1차 포장 유지\n\n\n\n" +
//                        "좋은 품질과 서비스로 찾아뵙기 위해 항상 노력하겠습니다.\n" +
//                        "추운 날씨 건강 유념하시고, 늘 행복하세요.\n\n" +
//                        "감사합니다.\n" +
//                        "마켓컬리 드림."
//        ));
//        boardRepository.saveAll(lists);
//    }

//    @Test
//    @Rollback(value = false)
//    void InsertFavo(){
////        Member mem = memberRepository.findById((long)1).orElseThrow(
////                () -> new ResourceNotFoundException()
////        );
////
////        List<Favorite> list = new ArrayList<>();
////
////        list.add(new Favorite(null, (long)5, mem));
////        list.add(new Favorite(null, (long)2, mem));
////        list.add(new Favorite(null, (long)9, mem));
////        list.add(new Favorite(null, (long)13, mem));
////        list.add(new Favorite(null, (long)129, mem));
////        list.add(new Favorite(null, (long)139, mem));
////        list.add(new Favorite(null, (long)111, mem));
////
////        favoriteRepository.saveAll(list);
//    }

//    @Test
//    @Rollback(value = false)
//    void InsertCart(){
//        Member member = memberRepository.findById((long)1).orElseThrow(
//                ResourceNotFoundException::new
//        );
//
//        List<Cart> cartList = new ArrayList<>();
//        Cart cart1 = new Cart(null, (long)1, 3, member);
//        Cart cart2 = new Cart(null, (long)135, 1,member);
//        Cart cart3 = new Cart(null, (long)41, 4,member);
//        Cart cart4 = new Cart(null, (long)77, 5,member);
//        cartList.add(cart1);
//        cartList.add(cart2);
//        cartList.add(cart3);
//        cartList.add(cart4);
//        cartRepository.saveAll(cartList);
//    }

//    @Test
//    @Rollback(value = false)
//    void InsertDeliverAddress(){
////        Member mem = memberRepository.findById((long)2).orElseThrow(
////                () -> new ResourceNotFoundException()
////        );
////        Deliver_Address da = new Deliver_Address(
////                null,
////                "경기도 고양시 고양동 고양이파트 351번지",
////                1,
////                mem
////        );
////        deliverAddressRepository.save(da);
//    }

//    @Test
//    @Rollback(value = false)
//    void InsertMemberInfo(){
////        Member member = new Member(
////                null,
////                "choiyusun",
////                "18181818",
////                "최유선",
////                "choiyusun@fastcampus.com",
////                "01099991111",
////                "여자",
////                "19891122",
////                "일반",
////                0,
////                "USER"
////        );
////        memberRepository.save(member);
//    }

//    // Insert Main page / Slide Images (v)
//    @Test
//    @Rollback(value = false)
//    void InsertSlideImgDb(){
//        List<Slide_img> slideImgList = new ArrayList<>();
//        String[] slideImgs ={
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1626426244.jpg",
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1626344232.jpg",
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1612347518.jpg",
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1626261408.jpg",
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1626322633.jpg",
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1626260202.jpg",
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1612094297.jpg",
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1625623216.jpg",
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1596164134.jpg",
//                "https://img-cf.kurly.com/shop/data/main/1/pc_img_1583112495.jpg"
//        };
//        for (String img : slideImgs) {
//            Slide_img slideImg = new Slide_img(null, img);
//            slideImgList.add(slideImg);
//        }
//        slideImgRepository.saveAll(slideImgList);
//    }

//    // Insert Main page / Insta Images, Landing URLs (v)
//    @Test
//    @Rollback(value = false)
//    void InsertInstaDb() throws Exception {
//        List<Insta_src> insta_srcList = new ArrayList<>();
//        // 인스타 Landing URL
//        String[] instaUrls = {
//                "https://www.instagram.com/p/CRTd4kIJcLB/",
//                "https://www.instagram.com/p/CRQhtrjpX17/",
//                "https://www.instagram.com/p/CRAj6bdpk-G/",
//                "https://www.instagram.com/p/CRAihvrp-h5/",
//                "https://www.instagram.com/p/CRAiZVop5hS/",
//                "https://www.instagram.com/p/CQzl9TXJnaL/",
//                "https://www.instagram.com/p/CQpiyRRJ-QC/",
//                "https://www.instagram.com/p/CQiaU3KJ9rN/",
//                "https://www.instagram.com/p/CQci4KBJjFY/",
//                "https://www.instagram.com/p/CQZ7QtipjW3/",
//                "https://www.instagram.com/p/CQPqD79phq4/",
//                "https://www.instagram.com/p/CQPp7ENJByn/",
//                "https://www.instagram.com/p/CQIS6OxpEV3/",
//                "https://www.instagram.com/p/CP9_jAIpQLX/",
//                "https://www.instagram.com/p/CP4Y1Tgpko-/",
//                "https://www.instagram.com/p/CP178Kzp2gt/",
//                "https://www.instagram.com/p/CPrkmbGJDkY/",
//                "https://www.instagram.com/p/CPkKYfKpKcx/",
//                "https://www.instagram.com/p/CPhY-H3ptjJ/",
//                "https://www.instagram.com/p/CPXBWULJDmr/"
//        };
//        // 인스타 Img URL
//        String[] instaImgs = {
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/217787090_4421425621208945_2623964379085059202_n.jpg?_nc_cat=101&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=j7IWA_haBzQAX9mhKcb&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=25aa3253fd2b66f20e325e3e3ac50ec2&oe=60FC6927",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/217880860_1065421947196970_8878709286796371299_n.jpg?_nc_cat=102&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=XVncyQn45OEAX-RvviL&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=ae05c84c9dec21d692a47ef5889e2e2b&oe=60FD822A",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/210551988_178324497604795_5373716652515546316_n.jpg?_nc_cat=102&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=EolN4948hwAAX8vgFh4&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=75c7122773dd560507f17926975081aa&oe=60FD1EC5",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/212050929_2611951445776357_6717020443434314024_n.jpg?_nc_cat=105&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=QdhVBc96IGMAX_7u5gZ&_nc_ht=scontent-nrt1-1.cdninstagram.com&edm=ANo9K5cEAAAA&oh=3bd9c4d21c86b5dc7dd4f9dc723d7208&oe=60FC9523",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/210868990_123643839840233_3997342401212751663_n.jpg?_nc_cat=101&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=hUht-NoJ9gIAX9ELLE2&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=f12d1d0756701172cf224e5f9fe6b6ca&oe=60FCC27E",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/209256934_563682511307839_6439455020536109910_n.jpg?_nc_cat=103&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=YbskNCbjRB4AX-wfRTo&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=9b725cd10e63273c5f95e23b0e3d24df&oe=60FCC488",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/208970613_331053075355894_1607974875794718739_n.jpg?_nc_cat=105&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=jGy2lKaY_aMAX-EGhiN&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=de1a814fd52324af86d0c9337aa5b4fb&oe=60FC107D",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/208594682_391246255639368_2930906952563692905_n.jpg?_nc_cat=111&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=YKp4RvrToiUAX_AbQez&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=ab96d1817fc6aaef1e64aa375884de40&oe=60FBCA3B",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/204975807_535015767645424_4512323582612749967_n.jpg?_nc_cat=102&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=NyTq38GrllAAX-vJngl&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=0da0bc9302300cba4b6533f1623de2f2&oe=60FCDADE",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/203992720_2962280777325287_6072648476173974479_n.jpg?_nc_cat=103&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=DUnsPpXNiwMAX_dXyDy&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=2bd25b7b510a82b82f85e60f75a9afa1&oe=60FBAEC7",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/202328169_535378717483197_6730880900395042541_n.jpg?_nc_cat=100&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=J_sd8ixGJMcAX-VtAfR&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=97f0798ef9eb4cad3477f1d8c106f7c6&oe=60FBE3A7",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/202046878_319173352995485_4556945369398009411_n.jpg?_nc_cat=108&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=-MSihr48L3QAX-7Leyy&_nc_ht=scontent-nrt1-1.cdninstagram.com&edm=ANo9K5cEAAAA&oh=ecaa7cc65167f03c502fadeca9680217&oe=60FCB7A8",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/200544367_167746445245557_3733803320861022205_n.jpg?_nc_cat=106&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=Pt-3aOeI2NgAX94T2X4&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=4bf1247c0b20547c94d8e0db5d5d06f6&oe=60FBCB6A",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/200768024_2917013425292642_5973334379347867911_n.jpg?_nc_cat=110&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=JC9ifImVKU8AX_ibPcD&_nc_ht=scontent-nrt1-1.cdninstagram.com&edm=ANo9K5cEAAAA&oh=9316461cff211c1ec0d2bf2b2d0acf0a&oe=60FCC1B6",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/199051331_336428437992123_4622818417024420468_n.jpg?_nc_cat=110&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=ce8oYiii98YAX_z2fPR&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=ddded439ee60954d243b2dbc65e82778&oe=60FCD808",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/197843741_173632057955857_1629541059183903619_n.jpg?_nc_cat=104&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=B8BpO5puEG4AX_DeVrH&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=986ec9b4438cc04858ffab4680d21ec8&oe=60FCBF9A",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/191952766_485200939455618_1445995895969973520_n.jpg?_nc_cat=111&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=jAs6J1d4S-EAX8SX7Ip&_nc_ht=scontent-nrt1-1.cdninstagram.com&edm=ANo9K5cEAAAA&oh=b64a8bd65ad563ef0a13c32219132d7d&oe=60FC0E70",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/193756819_213105403974226_3336028563214286027_n.jpg?_nc_cat=105&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=EB9MXECXteUAX9oIDoL&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=173435f3fa6788d0a23a14b4def42c71&oe=60FD5265",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/193647166_789959181893848_2864131780433851687_n.jpg?_nc_cat=105&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=-NIZxST9eVYAX_yaYPM&_nc_oc=AQmNTPmLjX-t_gIVGAqKigrodrY05LEd0SfbjCxkujSGgH87uSTNyYF02GelM6e6KQk&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=d571e77279362c3a64df89fe112d1cb7&oe=60FC27B7",
//                "https://scontent-nrt1-1.cdninstagram.com/v/t51.29350-15/191640927_330898418411090_8334589705079284754_n.jpg?_nc_cat=111&ccb=1-3&_nc_sid=8ae9d6&_nc_ohc=ZFCigawinXUAX_NWxS_&_nc_ht=scontent-nrt1-1.cdninstagram.com&oh=a2901f934534e043bc38e445cf8360fa&oe=60FC5ED7"
//        };
//        for(int i = 0; i < instaUrls.length; i++){
//            Insta_src instaSrc = new Insta_src(null, instaUrls[i], instaImgs[i]);
//            insta_srcList.add(instaSrc);
//        }
//        instaSrcRepository.saveAll(insta_srcList);
//    }

//    // Insert Crawling Product Data (v)
//    @Test
//    @Rollback(value = false)
//    void InsertProductDb() throws Exception {
//        JSONParser jsonParser = new JSONParser();
//        List<Product> productList = new ArrayList<>();
//
//        int categoryIdx = 0;
//        // 사전에 정해 놓은 카테고리 번호들
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
//        for(int i = 0; i < 321; i++){       // 총 Crawling 한 데이터 수 = 321
//            // 특정 카테고리의 개수만큼만 카운트
//            if(categoryNumList[categoryIdx] == 132){
//                if(cnt / 2 > 0){
//                    cnt = 0;
//                    categoryIdx++;
//                }
//            } else if(categoryNumList[categoryIdx] == 166){
//                if(cnt > 0){
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
//            // CrawlingData 파일 가져와 JSONObject로 변환
//            FileInputStream input = new FileInputStream("C:\\Crawling_Data\\" + i + ".json");
//            InputStreamReader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
//            BufferedReader in = new BufferedReader(reader);
//            JSONObject jsonObject = (JSONObject) jsonParser.parse(in);
//
//            // HTML Parsing Data List ( 0 = detail_img_url / 1 = detail_context / 2 = product_img_url
//            List<String> productDetailData = crawlingData(Integer.parseInt(jsonObject.get("no").toString()));
//            String detail_img_url = productDetailData != null ? productDetailData.get(0) : "";
//            String detail_context = productDetailData != null ? productDetailData.get(1) : "";
//            String product_img_url = productDetailData != null ? productDetailData.get(2) : "";
//
//            Product products = Product.builder()
//                    .id(null)
//                    .category(categoryNumList[categoryIdx])
//                    .detail_img_url(detail_img_url)
//                    .detail_context(detail_context)
//                    .product_img_url(product_img_url)
//                    .name(jsonObject.get("name").toString())
//                    .short_description(jsonObject.get("short_description").toString())
//                    .is_sales(Boolean.parseBoolean(jsonObject.get("is_sales").toString()))
//                    .unit_text(jsonObject.get("unit_text").toString())
//                    .weight(jsonObject.get("weight").toString())
//                    .origin(jsonObject.get("origin").toString())
//                    .contactant(jsonObject.get("contactant").toString())
//                    .brand_title(jsonObject.get("brand_title").toString())
//                    .expiration_date(jsonObject.get("expiration_date").toString())
//                    .guides(jsonObject.get("guides").toString())
//                    .delivery_time_type_text(jsonObject.get("delivery_time_type_text").toString())
//                    .packing_type_text(jsonObject.get("packing_type_text").toString())
//                    .original_price(Integer.parseInt(jsonObject.get("original_price").toString()))
//                    .discounted_price(Integer.parseInt(jsonObject.get("discounted_price").toString()))
//                    .discount_percent(Integer.parseInt(jsonObject.get("discount_percent").toString()))
//                    .discount_end_datetime(jsonObject.get("discount_end_datetime").toString())
//                    .original_image_url(jsonObject.get("original_image_url").toString())
//                    .main_image_url(jsonObject.get("main_image_url").toString())
//                    .list_image_url(jsonObject.get("list_image_url").toString())
//                    .detail_image_url(jsonObject.get("detail_image_url").toString())
//                    .sticker_image_url(jsonObject.get("sticker_image_url") != null ? jsonObject.get("sticker_image_url").toString() : "")
//                    .build();
//            productList.add(products);
//            cnt++;
//        }
//        productRepository.saveAll(productList);
//    }

//    @Test
//    @Rollback
//    void insertReview() throws Exception {
////        Member mem = new Member();
////        mem.setId(1L);
////
////        Product pro =  new Product();
////        pro.setId(319L);
////
////        Review review = new Review(
////                null,
////                "후기제목",
////                "후기내용",
////                "작성자",
////                LocalDate.now(),
////                3L,
////                10L,
////                pro,
////                mem
////        );
////
////        reviewRepository.save(review);
//        //verify(reviewRepository, times(1)).save(any(Review.class));
//    }

}