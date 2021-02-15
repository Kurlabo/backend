package com.kurlabo.backend.dto.testdto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MainTestDto {
    private String setbyul_img;
    private List<String> insta_thumbnail_img_url;
    private List<String> insta_landing_url;
    private List<String> slide_img;
    private Long product_id;
    private String short_description;
    private String name;
    private int original_price;
    private int discounted_price;
    private String original_image_url;
    private String sticker_image_url;
    private int category;
}
