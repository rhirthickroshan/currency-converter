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

    private final AuthMapper authMapper;

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final CustomerUserDetailsService customerUserDetailsService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<String> registerUser(RegisterRequest registerRequest)   {
        System.out.println(registerRequest);
        if (registerRequest == null) {
            throw new CurrencyException("Register Request is null");
        }
        String email = registerRequest.getEmail();
        if (email == null) {
            throw new CurrencyException("Please provide a valid email");
        }
        User userExistsByEmail = userService.findByEmail(email);
        if (userExistsByEmail != null) {
            throw new UserAlreadyExistsException("The user is already present for this email " + registerRequest.getEmail());
        }
        User user = authMapper.toUser(registerRequest);
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userService.createUser(user);
        return new ResponseEntity<>("User Registered Successfully ", HttpStatus.CREATED);
    }

    public ResponseEntity<?> loginUser(LoginRequest loginRequest) {
        if(loginRequest == null){
            throw new CurrencyException("Login Request is null. Please , provide a valid login detail");
        }
        String email = loginRequest.getEmail();
        if(email == null){
            throw new CurrencyException("The email cannot be null. Please , provide a valid email");
        }
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        UserDetails userDetails = getUserDetails(authentication, email);
        String token =  jwtService.generateToken(userDetails);
        if(token == null){
            throw new CurrencyException("The token is not created for email "+userDetails.getUsername());
        }
        return ResponseEntity.ok(Map.of("token" , token));
    }

    private static UserDetails getUserDetails(Authentication authentication, String email) {
        Object object = authentication.getPrincipal();
        if(object == null){
            throw new CurrencyException("Can't able to  authenticate for the email "+ email);
        }
        /*
        Here actually The Authentication Object will have UserDetails Service as this
        this uses the ProviderManager we will use the DaoAuthenticationProvider and then this use the UserDetails Service
        to loadByUserName okay :))

        * */
        return (UserDetails) object;
    }
}
