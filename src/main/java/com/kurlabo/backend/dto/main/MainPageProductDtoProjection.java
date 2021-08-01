package com.kurlabo.backend.dto.main;

public interface MainPageProductDtoProjection {
    Long getProduct_id();
    String getDetail_image_url();
    String getSticker_image_url();
    String getName();
    Integer getOriginal_price();
    Integer getDiscounted_price();
    Integer getDiscount_percent();
}
