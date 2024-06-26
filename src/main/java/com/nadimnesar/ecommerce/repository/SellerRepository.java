package com.nadimnesar.ecommerce.repository;

import com.nadimnesar.ecommerce.model.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<Seller, Integer> {
    Seller findByUserId(Integer userId);
}