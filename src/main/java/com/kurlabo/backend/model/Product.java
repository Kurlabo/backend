package com.kurlabo.backend.model;

import com.kurlabo.backend.converter.JsonConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;
    private int category;
    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "text")
    private String data;
    private String detail_img_url;
    private String detail_context;
    private String detail_title;
    private String product_img_url;
}
