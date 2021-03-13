package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.mypage.FavoriteProductDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Favorite;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.FavoriteRepository;
import com.kurlabo.backend.repository.ProductRepository;
import com.kurlabo.backend.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final ProductRepository productRepository;
    private final MemberService memberService;
    private final TokenProvider tokenProvider;

    public Page<FavoriteProductDto> getFavoriteList(String token, Pageable pageable){
        Member member = memberService.findById(tokenProvider.parseTokenToGetMemberId(token));
        List<Favorite> favoList = favoriteRepository.findByMember(member);
        List<FavoriteProductDto> productList = new ArrayList<>();
        for(Favorite list: favoList){
            Product product = productRepository.findById(list.getProducts_id()).orElseThrow(ResourceNotFoundException::new);
            FavoriteProductDto dto = new FavoriteProductDto(
                    product.getId(),
                    product.getList_image_url(),
                    product.getName(),
                    product.getOriginal_price(),
                    product.getDiscounted_price()
            );

            productList.add(dto);
        }

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), favoList.size());
        return new PageImpl<>(productList.subList(start, end), pageable, favoList.size());
    }

    @Transactional
    public Boolean insertFavorite(String token, Long product_id){
        Member member = memberService.findById(tokenProvider.parseTokenToGetMemberId(token));
        boolean bool = false;
        if(searchFavorite(member, product_id) == null){
            Favorite favorite = new Favorite(null, product_id, member);
            favoriteRepository.save(favorite);
            bool = true;
        }
        return bool;
    }

    @Transactional
    public Page<FavoriteProductDto> deleteFavorite(String token, List<Long> product_id, Pageable pageable){
        Member member = memberService.findById(tokenProvider.parseTokenToGetMemberId(token));
        List<Favorite> deleteLists = new ArrayList<>();
        for (Long idList : product_id) {
            Favorite deleteFavorite = favoriteRepository.findByMemberAndProductId(member, idList);
            if (deleteFavorite == null) {// 만약 들어온 product_id가 Favorite에 없다면 null 리턴 => 나중에 다른 예외처리로 바꿔야함
                return null;
            }
            deleteLists.add(deleteFavorite);
        }
        // member나 product_id가 맞지 않아 null값이 list에 저장되어 delete 불가 상태에서 오류가 발생하여 예외처리 해야 함.
        favoriteRepository.deleteAll(deleteLists);

        return getFavoriteList(token, pageable);
    }

    public Favorite searchFavorite(Member member, Long product_id){
        return favoriteRepository.findByMemberAndProductId(member, product_id);
    }

}
