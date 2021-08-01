package com.kurlabo.backend.dto.goods;

public interface RelatedProductDtoProjection {
    Long getProduct_id();
    String getName();
    String getOriginal_image_url();
    Integer getOriginal_price();
    Integer getDiscounted_price();
}
