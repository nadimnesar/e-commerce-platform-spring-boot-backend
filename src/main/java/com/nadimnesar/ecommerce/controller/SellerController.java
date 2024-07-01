package com.nadimnesar.ecommerce.controller;

import com.nadimnesar.ecommerce.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        return new ResponseEntity<>("add product", HttpStatus.OK);
    }
}
