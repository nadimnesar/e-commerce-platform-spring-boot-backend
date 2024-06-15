package com.ecommerce.main.model.entity;

import com.ecommerce.main.model.enums.ProductCategoryTypes;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer productId;

    private String productTitle;
    private String productBrand;

    @Enumerated(EnumType.STRING)
    private ProductCategoryTypes productCategory;

    private String productDescription;
    private String imageUrl;
    private Double price;
    private Integer stock;
}