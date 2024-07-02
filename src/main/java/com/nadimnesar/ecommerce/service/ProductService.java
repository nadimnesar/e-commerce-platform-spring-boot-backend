package com.nadimnesar.ecommerce.service;

import com.nadimnesar.ecommerce.enums.ProductCategory;
import com.nadimnesar.ecommerce.model.Product;
import com.nadimnesar.ecommerce.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ResponseEntity<?> getAll(Integer pageNo, Integer limit) {
        Pageable pageable = PageRequest.of(pageNo, limit);
        Page<Product> products = productRepository.findAll(pageable);
        if (products.isEmpty()) return new ResponseEntity<>("Not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    public ResponseEntity<?> getById(Integer productId) {
        if (productRepository.findById(productId).isPresent()) {
            Product product = productRepository.findById(productId).get();
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
        return new ResponseEntity<>("Not found.", HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> getAllByCategoryName(String categoryName, Integer pageNo, Integer limit) {
        ProductCategory type = ProductCategory.getCorrectType(categoryName);
        if (type == null) {
            HashMap<String, String> error = new HashMap<>();
            error.put("message", "Invalid product category");
            error.put("validCategories", String.join(", ", ProductCategory.getValidCategories()));
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
        Pageable pageable = PageRequest.of(pageNo, limit);
        Page<Product> products = productRepository.findByCategory(type, pageable);
        if (products.isEmpty()) return new ResponseEntity<>("Not found.", HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}