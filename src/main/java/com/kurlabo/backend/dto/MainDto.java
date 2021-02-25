package com.kurlabo.backend.dto;

import com.kurlabo.backend.dto.main.InstaSrcDto;
import com.kurlabo.backend.dto.main.MainPageProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MainDto {
    private Long id;
}