package com.nadimnesar.ecommerce.dto;

import com.nadimnesar.ecommerce.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String productTitle;
    private ProductCategory productCategory;
    private Double price;
    private Integer stock;

    private String productBrand;
    private String productDescription;
    private String imageUrl;
}