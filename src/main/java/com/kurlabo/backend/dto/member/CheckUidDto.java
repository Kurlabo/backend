package com.kurlabo.backend.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CheckUidDto {
    @NotBlank
    @NotNull
    private String checkUid;
}
