package com.ecommerce.main.repository;

import com.ecommerce.main.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {

}
