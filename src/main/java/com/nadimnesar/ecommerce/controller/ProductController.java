package com.nadimnesar.ecommerce.controller;

import com.nadimnesar.ecommerce.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(@RequestParam Integer pageNo, @RequestParam Integer limit) {
        return productService.getAll(pageNo, limit);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> getAllByCategoryName(@PathVariable String categoryName,
                                                  @RequestParam Integer pageNo,
                                                  @RequestParam Integer limit) {
        return productService.getAllByCategoryName(categoryName, pageNo, limit);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) {
        return productService.getById(id);
    }
}