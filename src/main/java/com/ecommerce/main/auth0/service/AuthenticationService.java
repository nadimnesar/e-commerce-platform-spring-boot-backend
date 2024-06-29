package com.ecommerce.main.auth0.service;

import com.ecommerce.main.auth0.dto.AuthResponseDto;
import com.ecommerce.main.auth0.dto.UserDto;
import com.ecommerce.main.auth0.enums.UserRoleTypes;
import com.ecommerce.main.auth0.model.User;
import com.ecommerce.main.auth0.repository.UserRepository;
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

    public boolean validUserDto(UserDto userDto) {
        if (userDto.getEmail() == null) return false;
        if(userDto.getPassword() == null) return false;
        return true;
    }

    public ResponseEntity<?> register(UserDto userDto, UserRoleTypes role) {
        if (!validUserDto(userDto)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        User user = new User();
        user.setName(userDto.getName());
        user.setMobileNumber(user.getMobileNumber());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(role);

        try {
            userRepository.save(user);
            String token = jwtService.generateToken(user);
            AuthResponseDto authResponseDto = new AuthResponseDto(token, role);
            return new ResponseEntity<>(authResponseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
    }

    public ResponseEntity<?> login(UserDto userDto) {
        if (!validUserDto(userDto)) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
        try {
            User user = userRepository.findByEmail(userDto.getEmail());
            String token = jwtService.generateToken(user);
            AuthResponseDto authResponseDto = new AuthResponseDto(token, user.getRole());
            return new ResponseEntity<>(authResponseDto, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}