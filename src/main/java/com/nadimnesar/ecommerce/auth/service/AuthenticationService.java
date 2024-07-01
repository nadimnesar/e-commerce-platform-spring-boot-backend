package com.nadimnesar.ecommerce.auth.service;

import com.nadimnesar.ecommerce.auth.dto.AuthResponseDto;
import com.nadimnesar.ecommerce.auth.dto.UserDto;
import com.nadimnesar.ecommerce.auth.enums.UserRoleTypes;
import com.nadimnesar.ecommerce.auth.model.User;
import com.nadimnesar.ecommerce.auth.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    public AuthenticationService(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder,
                                 UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public boolean invalidUserDto(UserDto userDto) {
        return (userDto.getPassword() == null) || (userDto.getEmail() == null);
    }

    public ResponseEntity<?> register(UserDto userDto, UserRoleTypes role) {
        if (invalidUserDto(userDto)) return new ResponseEntity<>(
                "Please provide both username and password.", HttpStatus.BAD_REQUEST);

        User user = new User();
        user.setName(userDto.getName());
        user.setMobileNumber(user.getMobileNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        try {
            String token = jwtService.generateToken(user);
            try {
                userRepository.save(user);
            } catch (Exception e) {
                return new ResponseEntity<>("Username already exists.", HttpStatus.CONFLICT);
            }
            AuthResponseDto authResponseDto = new AuthResponseDto(token, role);
            return new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while generating the token.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> login(UserDto userDto) {
        if (invalidUserDto(userDto)) return new ResponseEntity<>(
                "Please provide both username and password.", HttpStatus.BAD_REQUEST);

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
        AuthResponseDto authResponseDto = new AuthResponseDto(token, user.getRole());
        return new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
    }
}