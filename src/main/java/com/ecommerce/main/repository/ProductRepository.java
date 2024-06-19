package com.ecommerce.main.repository;

import com.ecommerce.main.enums.ProductCategoryTypes;
import com.ecommerce.main.model.Product;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByProductCategory(ProductCategoryTypes productCategory);
}