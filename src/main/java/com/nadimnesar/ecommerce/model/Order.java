package com.nadimnesar.ecommerce.model;

import com.nadimnesar.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
}