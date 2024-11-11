package com.nadimnesar.ecommerce.model;

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
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @OneToOne
    private Address address;

    @OneToOne
    private Cart cart;

    @OneToMany
    List<Order> orders;
}