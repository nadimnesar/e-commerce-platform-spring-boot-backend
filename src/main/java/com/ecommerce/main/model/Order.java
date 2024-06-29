package com.ecommerce.main.model;

import com.ecommerce.main.enums.OrderStatusTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

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

    private LocalDate orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatusTypes orderStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CartItem> cartItems;

    private Double totalPrice;

    @OneToOne
    private Address shippingAddress;
}