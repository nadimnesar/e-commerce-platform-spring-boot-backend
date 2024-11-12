package com.nadimnesar.ecommerce.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.nadimnesar.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne
    private Cart cart;

    private Double total;

    @OneToOne
    private Address shippingAddress;

    @ManyToOne
    @JsonBackReference
    private Customer customer;
}