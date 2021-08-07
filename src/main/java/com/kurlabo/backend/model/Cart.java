package com.kurlabo.backend.model;

import com.kurlabo.backend.dto.cart.CartProductDto;
import com.kurlabo.backend.exception.InvalidCartCntException;
import lombok.*;

import javax.persistence.*;

@Getter
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

    public void addCnt(int cnt){
        this.cnt = cnt;
    }

    public void updateCnt(int value){
        if(value < 0){
            if(this.cnt > 1){
                this.cnt += value;
            } else {
                throw new InvalidCartCntException("장바구니의 개수는 1개 미만이 될 수 없습니다.");
            }
        } else {
            this.cnt += value;
        }
    }
}
