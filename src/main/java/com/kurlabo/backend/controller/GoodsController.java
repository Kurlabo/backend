package com.kurlabo.backend.controller;


import com.kurlabo.backend.dto.ProductDto;
import com.kurlabo.backend.dto.goods.InsertCartDto;
import com.kurlabo.backend.model.Member;
import com.kurlabo.backend.service.GoodsService;
import com.kurlabo.backend.service.MemberService;
import com.kurlabo.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/api/goods")
public class GoodsController {

    private final MemberService memberService;
    private final GoodsService goodsService;
    private final ReviewService reviewService;

    // 장바구니 상품 추가
    @PostMapping("/goods_cart")
    public void insertCart(@RequestBody @Valid InsertCartDto dto){    // Security에서 member 가져와야함
        Member mem = memberService.findById((long)1);
        goodsService.insertCart(mem, dto.getProduct_id(), dto.getCnt());
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/{id}")
    public ProductDto goodDetail(@PathVariable(name = "id") Long id) {
//        reviewService.findReviewByProductId(id);
        return goodsService.goodDetail(id);
    }
}
