package com.nadimnesar.ecommerce.repository;

import com.nadimnesar.ecommerce.enums.ProductCategoryTypes;
import com.nadimnesar.ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductCategory(ProductCategoryTypes productCategory);
    Page<Product> findByProductCategory(ProductCategoryTypes productCategory, Pageable pageable);
}