package com.nadimnesar.ecommerce.model;

import com.nadimnesar.ecommerce.auth.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private User user;

    @OneToMany
    private List<Address> addresses;

    @OneToOne
    private Cart cart;

    @OneToMany
    List<Order> orders;
}