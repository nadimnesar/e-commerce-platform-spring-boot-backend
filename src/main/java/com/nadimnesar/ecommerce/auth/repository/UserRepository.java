package com.nadimnesar.ecommerce.auth.repository;

import com.nadimnesar.ecommerce.auth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String username);
}