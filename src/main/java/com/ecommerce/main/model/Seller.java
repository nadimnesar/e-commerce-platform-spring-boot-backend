package com.ecommerce.main.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer SellerId;

    String SellerName;
    String email;
    String mobileNumber;
    String password;

    @OneToMany
    List<Product> products;
}