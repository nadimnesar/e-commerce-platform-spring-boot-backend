package com.nadimnesar.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String line1;
    private String line2;
    private String city;
    private String state;
    private String postalCode;
    private String country;
}
