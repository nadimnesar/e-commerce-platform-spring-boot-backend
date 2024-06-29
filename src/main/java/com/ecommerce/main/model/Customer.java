package com.ecommerce.main.model;

import com.ecommerce.main.auth0.model.User;
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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private User customerUser;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Address> addresses;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart myCart;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Order> orders;
}