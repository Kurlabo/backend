package com.kurlabo.backend.dto.order;

import com.hazelcast.com.eclipsesource.json.JsonObject;
import com.kurlabo.backend.dto.ProductDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.json.simple.JSONObject;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderSheetResponseDto {
    @NotNull
    private String orderer_name;
    @NotNull
    private String orderer_phone;
    @NotNull
    private String orderer_email;
    @NotNull
    private String orderer_address;
}
