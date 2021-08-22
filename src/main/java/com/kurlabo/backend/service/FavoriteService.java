package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.mypage.FavoriteProductDto;
import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.exception.ExistDataException;
import com.kurlabo.backend.model.Favorite;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.FavoriteRepository;
import com.kurlabo.backend.repository.MemberRepository;
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
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;

    public Page<FavoriteProductDto> getFavoriteList(String token, Pageable pageable){
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        List<Favorite> favouriteList = favoriteRepository.findByMember(member);
        List<FavoriteProductDto> productList = new ArrayList<>();
        for(Favorite list: favouriteList){
            Product product = productRepository.findById(list.getProducts_id()).orElseThrow(() ->
                    new DataNotFoundException("해당 상품을 찾을 수 없습니다. Id = " + list.getProducts_id()));
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
        int end = Math.min((start + pageable.getPageSize()), favouriteList.size());
        return new PageImpl<>(productList.subList(start, end), pageable, favouriteList.size());
    }

    @Transactional
    public String insertFavorite(String token, Long product_id){
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        Favorite insertFavorite = favoriteRepository.findByMemberAndProductId(member, product_id)
                .orElse(null);

        if(insertFavorite != null){
            throw new ExistDataException("이미 늘 사는 것 리스트에 상품이 존재합니다.");
        }

        insertFavorite = Favorite.builder()
                .id(null)
                .products_id(product_id)
                .member(member)
                .build();

        favoriteRepository.save(insertFavorite);

        return "SUCCESS";
    }

    @Transactional
    public Page<FavoriteProductDto> deleteFavorite(String token, List<Long> product_id, Pageable pageable){
        Member member = memberRepository.findById(tokenProvider.parseTokenToGetMemberId(token)).orElseThrow(() ->
                new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + tokenProvider.parseTokenToGetMemberId(token)));
        List<Favorite> deleteLists = new ArrayList<>();
        for (Long idList : product_id) {
            Favorite deleteFavorite = favoriteRepository.findByMemberAndProductId(member, idList).orElseThrow(() ->
                    new DataNotFoundException("해당 상품을 찾을수 없습니다."));
            deleteLists.add(deleteFavorite);
        }
        favoriteRepository.deleteAll(deleteLists);

        return getFavoriteList(token, pageable);
    }
}
