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
public class OrderSheetRequestDto {
    @NotNull
    private Long member_id;
    @NotNull
    private List<Long> selected_cart_id;
}
