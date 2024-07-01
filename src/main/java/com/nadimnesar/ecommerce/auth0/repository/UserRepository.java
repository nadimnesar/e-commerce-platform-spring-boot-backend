package com.nadimnesar.ecommerce.auth0.repository;

import com.nadimnesar.ecommerce.auth0.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String username);
}