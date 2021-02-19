package com.kurlabo.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {
    private String no;
    private String name;
    private String short_description;
    private String long_description;
    private String is_sales;
    private String unit_text;
    private String weight;
    private String origin;
    private String contactant;
    private String brand_title;
    private String effective_date_start;
    private String effective_date_end;
    private String expiration_date;
    private String not_sales_text;
    private String extended_infos;
    private String today_brix;
    private List<String> guides;
    private String delivery_method;
    private String delivery_time_type_text;
    private String packing_type_text;
    private String delivery_type_text;
    private int delivery_price;
    private String delivery_price_text;
    private int buyable_kind;
    private int original_price;
    private int discounted_price;
    private int discount_percent;
    private boolean is_buy_now;
    private boolean is_sold_out;
    private boolean is_package_sold_out;
    private String sold_out_text;
    private int min_ea;
    private int max_ea;
    private String discount_start_datetime;
    private String discount_end_datetime;
    private String original_image_url;
    private String main_image_url;
    private String list_image_url;
    private String detail_image_url;
    private String sticker_image_url;
    private boolean is_package;
    private int package_type;
    private int review_count;
    private boolean is_purchase_status;
    private int discount_rate;
}
