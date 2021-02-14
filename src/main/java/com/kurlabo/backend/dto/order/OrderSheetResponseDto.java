package com.kurlabo.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSheetResponseDto {
    @NotNull
    private List<String> product_data;
    @NotNull
    private List<Integer> product_cnt;
    @NotNull
    private String orderer_name;
    @NotNull
    private String orderer_phone;
    @NotNull
    private String orderer_email;
    @NotNull
    private String orderer_address;
}
