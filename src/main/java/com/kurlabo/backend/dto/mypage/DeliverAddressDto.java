package com.kurlabo.backend.dto.mypage;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeliverAddressDto {

    private Long id;

    @NotNull
    private String deliver_address;

    @NotNull
    private String deliver_detail_address;

    @NotNull
    private int is_main;

    private String reciever;

    private String reciever_phone;

    private int checked;

    @QueryProjection
    public DeliverAddressDto(Long id, String deliver_address, String deliver_detail_address, int is_main, String reciever, String reciever_phone){
        this.id = id;
        this.deliver_address = deliver_address;
        this.deliver_detail_address = deliver_detail_address;
        this.is_main = is_main;
        this.reciever = reciever;
        this.reciever_phone = reciever_phone;
    }
}