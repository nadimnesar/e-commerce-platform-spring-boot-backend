package com.ecommerce.main.utils;

import com.ecommerce.main.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public class ProductResponseHelper {
    public static ResponseEntity<?> createResponse(List<Product> products){
        if(products.isEmpty()){
            HashMap<String, String> error = new HashMap<>();
            error.put("message", "Products not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return  new ResponseEntity<>(products, HttpStatus.OK);
    }

    public static ResponseEntity<?> createResponse(Product product){
        if (product == null) {
            HashMap<String, String> error = new HashMap<>();
            error.put("message", "Product not found");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}