package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.main.HeaderDto;
import com.kurlabo.backend.dto.main.InstaSrcDto;
import com.kurlabo.backend.dto.main.MainPageProductDtoProjection;
import com.kurlabo.backend.dto.main.MainResponseDto;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Deliver_Address;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.repository.*;
import com.kurlabo.backend.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MainService {

    private final SlideImgRepository slideImgRepository;
    private final InstaSrcRepository instaSrcRepository;
    private final MainSrcRepository mainSrcRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;
    private final CartRepository cartRepository;
    private final DeliverAddressRepository deliverAddressRepository;
    private final TokenProvider tokenProvider;

    public MainResponseDto getMainPage() {
        return MainResponseDto.builder()
                .slide_img_list(getSlideImages())
                .instaSrcDto(getInstaSrc())
                .setbyul_img(getSetbyulImg())
                .howAbout(setHowAbout())
                .frugality(setFrugality())
                .mdRecommend(setMdRecommend(0))
                .todays(setTodays())
                .hots(setHots())
                .goodPrice(setGoodPrice())
                .fastFood(setFastFood())
                .build();
    }

    public List<MainPageProductDtoProjection> setMdRecommend(int mainCategory){
        int[] categoryRange = setCategoryRange(mainCategory);
        return productRepository.findRandProductCategoryRange(categoryRange[0], categoryRange[1]);
    }

    public HeaderDto setHeader(String token) {
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        List<Cart> cartList = cartRepository.findByMember(member);
        Deliver_Address deliverAddress = deliverAddressRepository.findByMemberAndChecked(member, 1).orElseThrow(() ->
                new DataNotFoundException("해당 주소를 찾을 수 없습니다."));
        return new HeaderDto(member.getGrade(), member.getName(), deliverAddress.getDeliver_address(), cartList.size(), member.getUid());
    }

    private List<String> getSlideImages(){
        return slideImgRepository.findAllUrl();
    }

    private InstaSrcDto getInstaSrc(){
        return InstaSrcDto.builder()
                .landing_url_list(instaSrcRepository.findAllLandingUrl())
                .thumbnail_img_list(instaSrcRepository.findAllThumbnailImgUrl())
                .build();
    }

    private String getSetbyulImg(){
        return mainSrcRepository.findSetbyulImg();
    }

    private List<MainPageProductDtoProjection> setHowAbout(){
        return productRepository.findAllRandom(16);
    }

    private List<MainPageProductDtoProjection> setFrugality(){
        return productRepository.findDiscountPercentOverZero(16);
    }

    private List<MainPageProductDtoProjection> setTodays(){
        return setHowAbout();
    }

    private List<MainPageProductDtoProjection> setHots(){
        return setFrugality();
    }

    private List<MainPageProductDtoProjection> setGoodPrice(){
        return setFrugality();
    }

    private List<MainPageProductDtoProjection> setFastFood(){
        return productRepository.findRandProductCategoryRange(50, 59);
    }

    private int[] setCategoryRange(int mdCategory){
        int min = 0;
        int max = 0;
        // Enum 으로 바꿔보기
        switch (mdCategory){
            case 0: min = 0; max = 9; break;
            case 1: min = 10; max = 19; break;
            case 2: min = 20; max = 29; break;
            case 3: min = 30; max = 39; break;
            case 4: min = 40; max = 49; break;
            case 5: min = 50; max = 59; break;
            case 6: min = 60; max = 69; break;
            case 7: min = 70; max = 79; break;
            case 8: min = 80; max = 89; break;
            case 9: min = 90; max = 99; break;
            case 10: min = 100; max = 109; break;
            case 11: min = 110; max = 119; break;
            case 12: min = 120; max = 129; break;
            case 13: min = 130; max = 139; break;
            case 14: min = 140; max = 149; break;
            case 15: min = 150; max = 159; break;
            case 16: min = 160; max = 169; break;
        }
        return new int[] {min, max};
    }
}
