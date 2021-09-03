package com.kurlabo.backend.repository;

import com.kurlabo.backend.exception.DataNotFoundException;
import com.kurlabo.backend.model.Cart;
import com.kurlabo.backend.model.Member;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CartRepositoryTest {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("회원 장바구니 리스트 조회")
    @Test
    void findByMember(){
        Long memberId = 1L;
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));

        List<Cart> cartList = cartRepository.findByMember(member);

        assertThat(cartList.get(0).getId()).isEqualTo(2L);
        assertThat(cartList.get(0).getCnt()).isEqualTo(2L);
        assertThat(cartList.get(0).getProduct_id()).isEqualTo(3L);
        assertThat(cartList.get(0).getMember()).isEqualTo(member);
        assertThat(cartList.get(1).getId()).isEqualTo(3L);
        assertThat(cartList.get(1).getCnt()).isEqualTo(5L);
        assertThat(cartList.get(1).getProduct_id()).isEqualTo(15L);
        assertThat(cartList.get(1).getMember()).isEqualTo(member);
    }

    @DisplayName("회원 장바구니 상품 조회")
    @Test
    void findByMemberAndProduct_id(){
        Long memberId = 3L;
        Long productId = 9L;
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new DataNotFoundException("해당 회원정보를 찾을 수 없습니다. Id = " + memberId));

        Cart cart = cartRepository.findByMemberAndProduct_id(member, productId).orElseThrow(
                () -> new DataNotFoundException("해당 상품을 장바구니 리스트에서 찾을 수 없습니다." + productId));

        assertThat(cart.getId()).isEqualTo(1L);
        assertThat(cart.getCnt()).isEqualTo(5L);
        assertThat(cart.getProduct_id()).isEqualTo(9L);
        assertThat(cart.getMember()).isEqualTo(member);
    }
}