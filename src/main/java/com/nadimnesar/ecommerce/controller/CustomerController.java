package com.nadimnesar.ecommerce.controller;

import com.nadimnesar.ecommerce.dto.AddressDto;
import com.nadimnesar.ecommerce.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/updateAddress")
    public ResponseEntity<?> updateAddress(@RequestBody AddressDto addressDto) {
        return customerService.updateAddress(addressDto);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestParam Integer productId, @RequestParam Integer quantity) {
        return customerService.addToCart(productId, quantity);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @DeleteMapping("/removeFromCart")
    public ResponseEntity<?> removeFromCart(@RequestParam Integer productId, @RequestParam Integer quantity) {
        return customerService.removeFromCart(productId, quantity);
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/getCart")
    public ResponseEntity<?> getCart() {
        return customerService.getCart();
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @PostMapping("/createOrder")
    public ResponseEntity<?> createOrder() {
        return customerService.createOrder();
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @GetMapping("/getOrders")
    public ResponseEntity<?> getOrders() {
        return customerService.getOrders();
    }

    @PreAuthorize("hasAuthority('CUSTOMER')")
    @DeleteMapping("/cancelOrder")
    public ResponseEntity<?> cancelOrder(@RequestParam Integer orderId) {
        return customerService.cancelOrder(orderId);
    }
}
