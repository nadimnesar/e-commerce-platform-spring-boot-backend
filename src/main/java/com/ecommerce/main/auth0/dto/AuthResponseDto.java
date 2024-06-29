package com.ecommerce.main.auth0.dto;

import com.ecommerce.main.auth0.enums.UserRoleTypes;
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