package com.nadimnesar.ecommerce.model;

import com.nadimnesar.ecommerce.enums.ProductCategoryTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;

    @Column(nullable = false)
    private String productTitle;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductCategoryTypes productCategory;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer stock;

    private String productBrand;
    private String productDescription;
    private String imageUrl;
}