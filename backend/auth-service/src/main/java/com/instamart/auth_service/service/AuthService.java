package com.instamart.auth_service.service;

import com.instamart.auth_service.dto.LoginRequest;
// import com.instamart.auth_service.dto.LoginResponse;
import com.instamart.auth_service.dto.RegisterRequest;
// import com.instamart.auth_service.dto.RegisterResponse;
import com.instamart.auth_service.model.User;
import com.instamart.auth_service.repository.UserRepository;
import com.instamart.auth_service.security.JwtUtil;
// import com.instamart.auth_service.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void register(RegisterRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        repository.save(user);
    }

    public String login(LoginRequest request) {

        User user = repository.findByUsername(request.getUsername())
                .orElseThrow();

        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {

            return jwtUtil.generateToken(user.getUsername());
        }

        throw new RuntimeException("Invalid credentials");
    }
}