package com.kurlabo.backend.service;

import com.kurlabo.backend.dto.mypage.FavoriteProductDto;
import com.kurlabo.backend.exception.ResourceNotFoundException;
import com.kurlabo.backend.model.Favorite;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.model.Product;
import com.kurlabo.backend.repository.FavoriteRepository;
import com.kurlabo.backend.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
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

    public List<FavoriteProductDto> getFavoriteList(Member member, Pageable pageable){
        List<Favorite> favoList = favoriteRepository.findByMember(member, pageable);
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
        return productList;
    }

    @Transactional
    public String insertFavorite(Member member, Long product_id){
        String str = "이미 늘 사는 리스트에 존재하는 상품입니다.";
        if(searchFavorite(member, product_id) == null){
            str = "늘 사는 리스트에 추가 했습니다.";
            Favorite favorite = new Favorite(null, product_id, member);
            favoriteRepository.save(favorite);
        }
        return str;
    }

    @Transactional
    public List<FavoriteProductDto> deleteFavorite(Member member, List<Long> product_id, Pageable pageable){
        List<Favorite> deleteLists = new ArrayList<>();
        for(int i = 0; i < product_id.size(); i++){
            Favorite deleteFavorite = favoriteRepository.findByMemberAndProductId(member, product_id.get(i));
            deleteLists.add(deleteFavorite);
        }
        // member나 product_id가 맞지 않아 null값이 list에 저장되어 delete 불가 상태에서 오류가 발생하여 예외처리 해야 함.
        favoriteRepository.deleteAll(deleteLists);

        return getFavoriteList(member, pageable);
    }

    public Favorite searchFavorite(Member member, Long product_id){
        return favoriteRepository.findByMemberAndProductId(member, product_id);
    }

}
