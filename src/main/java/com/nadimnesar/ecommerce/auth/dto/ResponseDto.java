package com.nadimnesar.ecommerce.auth.dto;

import com.nadimnesar.ecommerce.auth.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseDto {
    private String jwt;
    private UserRole userRole;
}