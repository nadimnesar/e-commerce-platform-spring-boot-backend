package com.nadimnesar.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nadimnesar.ecommerce.auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Seller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @OneToMany
    @JsonManagedReference
    private List<Product> products;
}