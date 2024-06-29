package com.ecommerce.main.auth0.controller;

import com.ecommerce.main.auth0.dto.UserDto;
import com.ecommerce.main.auth0.enums.UserRoleTypes;
import com.ecommerce.main.auth0.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {

    AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postRegistration(@RequestBody UserDto userDto) {
        return authenticationService.register(userDto, UserRoleTypes.CUSTOMER);
    }

    @PostMapping("/seller/register")
    public ResponseEntity<?> postSellerRegistration(@RequestBody UserDto userDto) {
        return authenticationService.register(userDto, UserRoleTypes.SELLER);
    }

    @GetMapping("/login")
    public ResponseEntity<?> getLogin(@RequestBody UserDto userDto) {
        return authenticationService.login(userDto);
    }
}