package com.nadimnesar.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nadimnesar.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Date date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne
    private Cart cart;

    @ManyToOne
    private Address shippingAddress;

    @ManyToOne
    @JsonBackReference
    private Customer customer;
}