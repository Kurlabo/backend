package com.kurlabo.backend.dto.main;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InstaSrcDto {
    private List<String> landing_url_list;
    private List<String> thumbnail_img_list;
}
