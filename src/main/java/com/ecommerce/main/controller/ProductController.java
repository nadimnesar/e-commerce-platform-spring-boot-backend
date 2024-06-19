package com.ecommerce.main.controller;

import com.ecommerce.main.model.Product;
import com.ecommerce.main.service.ProductService;
import com.ecommerce.main.utils.ProductResponseHelper;
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
        return ProductResponseHelper.createResponse(object);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getById(@PathVariable Integer productId) {
        Product product = productService.getById(productId);
        return ProductResponseHelper.createResponse(product);
    }

    @GetMapping("/products/{categoryName}")
    public ResponseEntity<?> getAllByCategoryName(@PathVariable String categoryName,
                                                  @RequestParam(required = false) Integer pageNo,
                                                  @RequestParam(required = false) Integer limit) {
        Object products = productService.getAllByCategoryName(categoryName, pageNo, limit);
        return ProductResponseHelper.createResponse(products);
    }
}