package com.instamart.auth_service.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instamart.auth_service.dto.JwtResponse;
import com.instamart.auth_service.dto.LoginRequest;
import com.instamart.auth_service.dto.RegisterRequest;
import com.instamart.auth_service.security.JwtUtil;
// import com.instamart.auth_service.security.UserPrincipal;
import com.instamart.auth_service.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
        // private final AuthenticationManager authenticationManager;
        // private final JwtUtil jwtUtils;
        private final AuthService authService;

        @PostMapping("/register")
        public String register(@RequestBody RegisterRequest request) {

                authService.register(request);

                return "User Created";
        }

        // @PostMapping("/login")
        // public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest
        // loginRequest) {
        // System.out.println("Login attempt for user: " + loginRequest.getUsername() +
        // ", password: "
        // + loginRequest.getPassword());
        // Authentication authentication = authenticationManager.authenticate(
        // new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
        // loginRequest.getPassword()));

        // SecurityContextHolder.getContext().setAuthentication(authentication);
        // String jwt = jwtUtils.generateJwtToken(authentication);

        // UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        // List<String> roles = userDetails.getAuthorities().stream()
        // .map(GrantedAuthority::getAuthority)
        // .collect(Collectors.toList());

        // return ResponseEntity.ok(new JwtResponse(jwt,
        // userDetails.getUsername(),
        // userDetails.getUser().getEmail(),
        // roles));
        // }
        @PostMapping("/login")
        public String login(@RequestBody LoginRequest request) {
                System.out.println("Login attempt for user: " + request.getUsername() + ", password: "
                                + request.getPassword());

                return authService.login(request);
        }
}