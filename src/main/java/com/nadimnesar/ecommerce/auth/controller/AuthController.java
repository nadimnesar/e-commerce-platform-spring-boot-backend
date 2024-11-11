package com.nadimnesar.ecommerce.auth.controller;

import com.nadimnesar.ecommerce.auth.dto.UserDto;
import com.nadimnesar.ecommerce.auth.enums.UserRole;
import com.nadimnesar.ecommerce.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegistration(@RequestBody UserDto userDto) {
        return authenticationService.register(userDto, UserRole.CUSTOMER);
    }

    @PostMapping("/seller/register")
    public ResponseEntity<?> postSellerRegistration(@RequestBody UserDto userDto) {
        return authenticationService.register(userDto, UserRole.SELLER);
    }

    @PostMapping("/login")
    public ResponseEntity<?> getLogin(@RequestBody UserDto userDto) {
        return authenticationService.login(userDto);
    }
}