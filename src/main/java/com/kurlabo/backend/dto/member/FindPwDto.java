package com.kurlabo.backend.dto.member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindPwDto {
    @NotEmpty @NotNull
    private String name;

    @NotEmpty @NotNull
    private String uid;

    @NotEmpty @NotNull
    private String email;
}
