package com.nadimnesar.ecommerce.dto;


import com.nadimnesar.ecommerce.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private Integer id;
    private List<CartItem> items;
    private Double total;
}
