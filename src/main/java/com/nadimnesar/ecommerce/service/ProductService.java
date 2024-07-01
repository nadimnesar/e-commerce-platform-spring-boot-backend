package com.nadimnesar.ecommerce.service;

import com.nadimnesar.ecommerce.enums.ProductCategory;
import com.nadimnesar.ecommerce.model.Product;
import com.nadimnesar.ecommerce.repository.ProductRepository;
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

    public Object getAll(Integer pageNo, Integer limit) {
        if (pageNo != null && limit != null) {
            Pageable pageable = PageRequest.of(pageNo, limit);
            return productRepository.findAll(pageable);
        } else {
            return productRepository.findAll();
        }
    }

    public Product getById(Integer productId) {
        if (productRepository.findById(productId).isPresent())
            return productRepository.findById(productId).get();
        return null;
    }

    public Object getAllByCategoryName(String categoryName, Integer pageNo, Integer limit) {
        ProductCategory type = ProductCategory.getCorrectType(categoryName);
        if (type == null) {
            HashMap<String, String> error = new HashMap<>();
            error.put("message", "Invalid product category");
            error.put("validCategories", String.join(", ", ProductCategory.getValidCategories()));
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (pageNo != null && limit != null) {
            Pageable pageable = PageRequest.of(pageNo, limit);
            return productRepository.findByCategory(type, pageable);
        } else {
            return productRepository.findByCategory(type);
        }
    }
}