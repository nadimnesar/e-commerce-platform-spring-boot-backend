package com.nadimnesar.ecommerce.controller;

import com.nadimnesar.ecommerce.model.Product;
import com.nadimnesar.ecommerce.service.ProductService;
import com.nadimnesar.ecommerce.utils.ProductResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/all")
    public ResponseEntity<?> getAll(@RequestParam(required = false) Integer pageNo,
                                    @RequestParam(required = false) Integer limit) {
        Object object = productService.getAll(pageNo, limit);
        return ProductResponseUtil.createResponse(object);
    }

    @GetMapping("/products/{categoryName}")
    public ResponseEntity<?> getAllByCategoryName(@PathVariable String categoryName,
                                                  @RequestParam(required = false) Integer pageNo,
                                                  @RequestParam(required = false) Integer limit) {
        Object object = productService.getAllByCategoryName(categoryName, pageNo, limit);
        return ProductResponseUtil.createResponse(object);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getById(@PathVariable Integer productId) {
        Product product = productService.getById(productId);
        return ProductResponseUtil.createResponse(product);
    }
}