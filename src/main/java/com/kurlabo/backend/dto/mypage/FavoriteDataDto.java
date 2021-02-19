package com.kurlabo.backend.dto.mypage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteDataDto {
    private Long favorite_id;
    private Long products_id;
    private Long member_id;
    private FavoriteProductDto favoriteProductDto;
}
