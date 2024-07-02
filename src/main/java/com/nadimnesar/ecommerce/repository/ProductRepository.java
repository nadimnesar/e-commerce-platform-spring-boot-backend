package com.nadimnesar.ecommerce.repository;

import com.nadimnesar.ecommerce.enums.ProductCategory;
import com.nadimnesar.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Page<Product> findByCategory(ProductCategory productCategory, Pageable pageable);
}