package com.ecommerce.main.service;

import com.ecommerce.main.enums.ProductCategoryTypes;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAll() {
        return productRepository.findAll();
    }

    public Page<Product> getAll(Integer pageNo, Integer limit) {
        Pageable pageable = PageRequest.of(pageNo, limit);
        return productRepository.findAll(pageable);
    }

    public List<Product> getAllByCategoryName(ProductCategoryTypes categoryName) {
        return productRepository.findByProductCategory(categoryName);
    }

    public Page<Product> getAllByCategoryName(ProductCategoryTypes categoryName, Integer pageNo, Integer limit) {
        Pageable pageable = PageRequest.of(pageNo, limit);
        return productRepository.findByProductCategory(categoryName, pageable);
    }

    public Product getById(Integer productId) {
        if (productRepository.findById(productId).isPresent())
            return productRepository.findById(productId).get();
        return null;
    }
}