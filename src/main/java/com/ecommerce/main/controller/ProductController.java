package com.ecommerce.main.controller;

import com.ecommerce.main.enums.ProductCategoryTypes;
import com.ecommerce.main.model.Product;
import com.ecommerce.main.service.ProductService;
import com.ecommerce.main.utils.ProductResponseHelper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

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
        if(pageNo != null && limit != null){
            Page<Product> productsPage = productService.getAll(pageNo, limit);
            return ProductResponseHelper.createResponse(productsPage);
        }
        else {
            List<Product> products = productService.getAll();
            return ProductResponseHelper.createResponse(products);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getById(@PathVariable Integer productId) {
        Product product = productService.getById(productId);
        return ProductResponseHelper.createResponse(product);
    }

    @GetMapping("/products/{categoryName}")
    public ResponseEntity<?> getAllByCategoryName(@PathVariable String categoryName) {
        ProductCategoryTypes type = ProductCategoryTypes.getCorrectType(categoryName);
        if (type == null) {
            HashMap<String, String> error = new HashMap<>();
            error.put("message", "Invalid product category");
            error.put("validCategories", String.join(", ", ProductCategoryTypes.getValidCategories()));
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }

        List<Product> products = productService.getAllByCategoryName(type);
        return ProductResponseHelper.createResponse(products);
    }
}