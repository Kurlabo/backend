package com.kurlabo.backend.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CheckoutRequestDto {
    @NotNull
    private Long member_id;
    @NotNull
    private String reciever;
    @NotNull
    private String reciever_phone;
    @NotNull
    private String reciever_post;
    @NotNull
    private String reciever_place;
    @NotNull
    private String reciever_visit_method;
    @NotNull
    private String arrived_alarm;
    @NotNull
    private LocalDate checkout_date;
    @NotNull
    private String checkout;
    @NotNull
    private List<Long> product_id_list;
    @NotNull
    private int total_cost;
}
