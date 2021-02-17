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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        return new ProductDto(product);
    }

}
