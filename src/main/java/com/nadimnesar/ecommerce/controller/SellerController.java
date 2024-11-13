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
        return sellerService.addProduct(productDto);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @GetMapping("/products")
    public ResponseEntity<?> getProducts(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        return sellerService.getMyProducts(pageNo, limit);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PutMapping("/updateProduct")
    public ResponseEntity<?> updateProduct(@RequestParam Integer id, @RequestBody ProductDto productDto) {
        return sellerService.updateProduct(id, productDto);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @DeleteMapping("/deleteProduct")
    public ResponseEntity<?> deleteProduct(@RequestParam Integer id) {
        return sellerService.deleteProduct(id);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @PostMapping("/addStock")
    public ResponseEntity<?> addStock(@RequestParam Integer productId, @RequestParam Integer stock) {
        return sellerService.addStock(productId, stock);
    }

    @PreAuthorize("hasAuthority('SELLER')")
    @DeleteMapping("/removeStock")
    public ResponseEntity<?> removeStock(@RequestParam Integer productId, @RequestParam Integer stock) {
        return sellerService.removeStock(productId, stock);
    }
}