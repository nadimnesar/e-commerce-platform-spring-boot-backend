package com.nadimnesar.ecommerce.auth.service;

import com.nadimnesar.ecommerce.auth.dto.ResponseDto;
import com.nadimnesar.ecommerce.auth.dto.UserDto;
import com.nadimnesar.ecommerce.auth.enums.UserRole;
import com.nadimnesar.ecommerce.auth.model.User;
import com.nadimnesar.ecommerce.auth.repository.UserRepository;
import com.nadimnesar.ecommerce.model.Cart;
import com.nadimnesar.ecommerce.model.Customer;
import com.nadimnesar.ecommerce.model.Seller;
import com.nadimnesar.ecommerce.repository.CartRepository;
import com.nadimnesar.ecommerce.repository.CustomerRepository;
import com.nadimnesar.ecommerce.repository.SellerRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final SellerRepository sellerRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                                 UserRepository userRepository, JwtService jwtService, SellerRepository sellerRepository, CustomerRepository customerRepository, CartRepository cartRepository) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.sellerRepository = sellerRepository;
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
    }

    public boolean invalidUserDto(UserDto userDto) {
        return (userDto.getPassword() == null) || (userDto.getEmail() == null) ||
                (userDto.getName() == null) || (userDto.getMobileNumber() == null);
    }

    public ResponseEntity<?> register(UserDto userDto, UserRole role) {
        if (invalidUserDto(userDto)) return new ResponseEntity<>(
                "Please provide name, mobileNumber, email, password.", HttpStatus.BAD_REQUEST);

        User user = new User();
        user.setName(userDto.getName());
        user.setMobileNumber(userDto.getMobileNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        try {
            String token = jwtService.generateToken(user);
            try {
                userRepository.save(user);
                if (user.getRole() == UserRole.SELLER) {
                    Seller seller = new Seller();
                    seller.setUser(user);
                    seller.setProducts(new ArrayList<>());
                    sellerRepository.save(seller);
                } else {
                    Customer customer = new Customer();
                    customer.setUser(user);
                    customer.setAddresses(new ArrayList<>());
                    Cart cart = new Cart();
                    cart.setItems(new ArrayList<>());
                    cart.setTotal(0.0);
                    cartRepository.save(cart);
                    customer.setCart(cart);
                    customer.setOrders(new ArrayList<>());
                    customerRepository.save(customer);
                }
            } catch (Exception e) {
                return new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);
            }
            ResponseDto responseDto = new ResponseDto(token, role);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while generating the token.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> login(UserDto userDto) {
        if (invalidUserDto(userDto)) return new ResponseEntity<>(
                "Please provide both email and password.", HttpStatus.BAD_REQUEST);

        User user = userRepository.findByEmail(userDto.getEmail());
        if (user == null) return new ResponseEntity<>("User not found.", HttpStatus.UNAUTHORIZED);

        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
            authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            return new ResponseEntity<>("Incorrect password.", HttpStatus.UNAUTHORIZED);
        }

        String token = jwtService.generateToken(user);
        ResponseDto responseDto = new ResponseDto(token, user.getRole());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}