package com.nadimnesar.ecommerce.service;

import com.nadimnesar.ecommerce.auth.model.User;
import com.nadimnesar.ecommerce.dto.ProductDto;
import com.nadimnesar.ecommerce.model.Product;
import com.nadimnesar.ecommerce.model.Seller;
import com.nadimnesar.ecommerce.repository.ProductRepository;
import com.nadimnesar.ecommerce.repository.SellerRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SellerService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;

    public SellerService(ProductRepository productRepository, SellerRepository sellerRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
    }

    @Transactional
    public ResponseEntity<?> addProductService(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Seller seller = sellerRepository.findByUserId(((User) userDetails).getId());
        product.setSeller(seller);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    "Please provide title, brand, category, description, price and stock.", HttpStatus.BAD_REQUEST);
        }

        seller.getProducts().add(product);
        sellerRepository.save(seller);
        return new ResponseEntity<>("Product added successfully.", HttpStatus.CREATED);
    }
}