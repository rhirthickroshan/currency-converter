package com.currency.service;

import com.currency.dto.LoginRequest;
import com.currency.dto.RegisterRequest;
import com.currency.entity.User;
import com.currency.exception.custom.CurrencyException;
import com.currency.exception.custom.UserAlreadyExistsException;
import com.currency.mapper.AuthMapper;
import com.currency.security.CustomerUserDetailsService;
import com.currency.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    private final AuthMapper authMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomerUserDetailsService customerUserDetailsService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> registerUser(RegisterRequest registerRequest) {

        logger.info("Registration request received.");

        if (registerRequest == null) {
            logger.error("Register request is null.");
            throw new CurrencyException("Register Request is null");
        }

        String email = registerRequest.getEmail();

        logger.debug("Validating registration request for email: {}", email);

        if (email == null) {
            logger.warn("Email is null in registration request.");
            throw new CurrencyException("Please provide a valid email");
        }

        logger.debug("Checking if user already exists with email: {}", email);

        User existingUser = userService.findByEmail(email);

        if (existingUser != null) {
            logger.warn("Registration failed. User already exists with email: {}", email);
            throw new UserAlreadyExistsException(
                    "The user is already present for this email " + email);
        }

        logger.debug("Mapping RegisterRequest to User entity.");

        User user = authMapper.toUser(registerRequest);

        logger.debug("Encoding password.");

        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        logger.info("Saving new user with email: {}", email);

        userService.createUser(user);

        logger.info("User registered successfully with email: {}", email);

        return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {

        logger.info("Login request received.");

        if (loginRequest == null) {
            logger.error("Login request is null.");
            throw new CurrencyException(
                    "Login Request is null. Please provide valid login details.");
        }

        String email = loginRequest.getEmail();

        logger.debug("Authenticating user: {}", email);

        if (email == null) {
            logger.warn("Email is null during login.");
            throw new CurrencyException(
                    "The email cannot be null. Please provide a valid email.");
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));

        logger.info("Authentication successful for email: {}", email);

        UserDetails userDetails = getUserDetails(authentication, email);

        logger.debug("Generating JWT token.");

        String token = jwtService.generateToken(userDetails);

        if (token == null) {
            logger.error("JWT token generation failed for user: {}", email);
            throw new CurrencyException(
                    "The token is not created for email " + userDetails.getUsername());
        }

        logger.info("JWT generated successfully for email: {}", email);

        return ResponseEntity.ok(Map.of("token", token));
    }

    private static UserDetails getUserDetails(Authentication authentication,
                                              String email) {

        logger.debug("Fetching authenticated user details.");

        Object principal = authentication.getPrincipal();

        if (principal == null) {
            logger.error("Authentication principal is null for email: {}", email);
            throw new CurrencyException(
                    "Can't authenticate user for email " + email);
        }

        logger.debug("UserDetails loaded successfully.");

        return (UserDetails) principal;
    }
}