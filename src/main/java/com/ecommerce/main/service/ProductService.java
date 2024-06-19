package com.ecommerce.main.service;

import com.ecommerce.main.enums.ProductCategoryTypes;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.repository.ProductRepository;
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
        ProductCategoryTypes type = ProductCategoryTypes.getCorrectType(categoryName);
        if (type == null) {
            HashMap<String, String> error = new HashMap<>();
            error.put("message", "Invalid product category");
            error.put("validCategories", String.join(", ", ProductCategoryTypes.getValidCategories()));
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        if (pageNo != null && limit != null) {
            Pageable pageable = PageRequest.of(pageNo, limit);
            return productRepository.findByProductCategory(type, pageable);
        } else {
            return productRepository.findByProductCategory(type);
        }
    }
}