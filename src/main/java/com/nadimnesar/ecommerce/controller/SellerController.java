package com.nadimnesar.ecommerce.controller;

import com.nadimnesar.ecommerce.dto.ProductDto;
import com.nadimnesar.ecommerce.service.SellerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/seller")
public class SellerController {

    private final SellerService sellerService;

    public SellerController(SellerService sellerService) {
        this.sellerService = sellerService;
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto) {
        return sellerService.addProductService(productDto);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        return sellerService.getMyProducts(pageNo, limit);
    }
}