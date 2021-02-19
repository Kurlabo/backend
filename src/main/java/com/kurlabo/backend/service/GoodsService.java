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

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodsService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

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
