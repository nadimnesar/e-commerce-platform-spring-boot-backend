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

import java.util.Objects;

@Service
public class SellerService {

    private final ProductRepository productRepository;
    private final SellerRepository sellerRepository;
    private final ProductService productService;

    public SellerService(ProductRepository productRepository, SellerRepository sellerRepository,
                         ProductService productService) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.productService = productService;
    }

    private Seller getSellerInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return sellerRepository.findByUserId(((User) userDetails).getId());
    }

    @Transactional
    public ResponseEntity<?> addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setTitle(productDto.getTitle());
        product.setBrand(productDto.getBrand());
        product.setCategory(productDto.getCategory());
        product.setDescription(productDto.getDescription());
        product.setImageUrl(productDto.getImageUrl());
        product.setPrice(productDto.getPrice());
        product.setStock(productDto.getStock());

        Seller seller = getSellerInfo();
        product.setSeller(seller);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            return new ResponseEntity<>("Please provide title, brand, category, description, price and stock.",
                    HttpStatus.BAD_REQUEST);
        }
        seller.getProducts().add(product);
        sellerRepository.save(seller);
        return new ResponseEntity<>("Product added successfully.", HttpStatus.CREATED);
    }

    public ResponseEntity<?> getMyProducts(Integer pageNo, Integer limit) {
        return productService.getProductsBySellerId(getSellerInfo().getId(), pageNo, limit);
    }

    public ResponseEntity<?> updateProduct(Integer productId, ProductDto productDto) {
        if (productRepository.findById(productId).isPresent()) {
            Product product = productRepository.findById(productId).get();
            if (Objects.equals(product.getSeller().getId(), getSellerInfo().getId())) {
                if (productDto.getTitle() != null) product.setTitle(productDto.getTitle());
                if (productDto.getBrand() != null) product.setBrand(productDto.getBrand());
                if (productDto.getCategory() != null) product.setCategory(productDto.getCategory());
                if (productDto.getDescription() != null) product.setDescription(productDto.getDescription());
                if (productDto.getImageUrl() != null) product.setImageUrl(productDto.getImageUrl());
                if (productDto.getPrice() != null) product.setPrice(productDto.getPrice());
                if (productDto.getStock() != null) product.setStock(productDto.getStock());
                productRepository.save(product);
                return new ResponseEntity<>("Product updated successfully.", HttpStatus.OK);
            }
            return new ResponseEntity<>("This product own my someone else.", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);
    }

    @Transactional
    public ResponseEntity<?> deleteProduct(Integer productId) {
        if (productRepository.findById(productId).isPresent()) {
            Product product = productRepository.findById(productId).get();
            Seller seller = getSellerInfo();
            if (Objects.equals(product.getSeller().getId(), seller.getId())) {
                seller.getProducts().remove(product);
                sellerRepository.save(seller);
                productRepository.delete(product);
                return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("This product own my someone else.", HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>("Product not found.", HttpStatus.NOT_FOUND);
        }
    }
}