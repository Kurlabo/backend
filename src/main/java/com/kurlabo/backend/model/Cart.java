package com.kurlabo.backend.model;

import com.kurlabo.backend.dto.cart.CartProductDto;
import com.kurlabo.backend.dto.cart.GetCartResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cart_id")
    private Long id;
    private Long product_id;
    private int cnt;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public CartProductDto toCartProductDto(Product product) {
        return CartProductDto.builder()
                .product_id(product.getId())
                .name(product.getName())
                .original_price(product.getOriginal_price())
                .discounted_price(product.getDiscounted_price())
                .packing_type_text(product.getPacking_type_text())
                .min_ea(1)
                .max_ea(99)
                .list_image_url(product.getList_image_url())
                .cnt(this.getCnt())
                .reduced_price(product.getOriginal_price()-product.getDiscounted_price())
                .build();
    }
}
