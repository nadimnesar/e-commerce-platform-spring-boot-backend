package com.ecommerce.main.utils;

import com.ecommerce.main.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.List;

public class ProductResponseUtil {
    public static ResponseEntity<?> createResponse(Object object) {
        HashMap<String, String> error = new HashMap<>();
        error.put("message", "Not found.");

        if (object instanceof List<?>) {
            List<Product> productsList = (List<Product>) object;
            if (productsList.isEmpty()) return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            else return new ResponseEntity<>(productsList, HttpStatus.OK);
        }

        if (object instanceof Page<?>) {
            Page<Product> productsPage = (Page<Product>) object;
            if (productsPage.isEmpty()) return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
            else return new ResponseEntity<>(productsPage, HttpStatus.OK);
        }
        return (ResponseEntity<?>) object;
    }

    public static ResponseEntity<?> createResponse(Product product) {
        if (product == null) {
            HashMap<String, String> error = new HashMap<>();
            error.put("message", "Not found.");
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        } else return new ResponseEntity<>(product, HttpStatus.OK);
    }
}