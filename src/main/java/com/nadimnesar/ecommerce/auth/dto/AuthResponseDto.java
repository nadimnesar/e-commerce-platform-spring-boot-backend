package com.nadimnesar.ecommerce.auth.dto;

import com.nadimnesar.ecommerce.auth.enums.UserRoleTypes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponseDto {
    private String jwt;
    private UserRoleTypes userRoleType;
}