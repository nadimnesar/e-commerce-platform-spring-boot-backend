package com.nadimnesar.ecommerce.dto;

import com.nadimnesar.ecommerce.enums.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private String title;
    private String brand;
    private ProductCategory category;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer stock;
}