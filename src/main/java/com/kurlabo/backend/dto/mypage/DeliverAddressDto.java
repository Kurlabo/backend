package com.kurlabo.backend.dto.mypage;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class DeliverAddressDto {

    private Long id;

    private String deliver_address;

    private String deliver_detail_address;

    private int is_main;

    private String reciever;

    private String reciever_phone;

    private int checked;

    @QueryProjection
    public DeliverAddressDto(Long id, String deliver_address, String deliver_detail_address, int is_main, String reciever, String reciever_phone, int checked){
        this.id = id;
        this.deliver_address = deliver_address;
        this.deliver_detail_address = deliver_detail_address;
        this.is_main = is_main;
        this.reciever = reciever;
        this.reciever_phone = reciever_phone;
        this.checked = checked;
    }
}