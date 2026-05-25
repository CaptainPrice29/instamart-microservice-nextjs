package com.instamart.user_service.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.instamart.user_service.config.exception.ResourceNotFoundException;
import com.instamart.user_service.dto.UsersEmailDTO;
import com.instamart.user_service.model.User;
import com.instamart.user_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<UsersEmailDTO> findAllUsersEmail() {
        return userRepository.findAll().stream().map(
                user -> new UsersEmailDTO(user.getUsername(), user.getEmail(), user.getFirstName(), user.getLastName()))
                .toList();
    }
    public User findById(String id){
        Objects.requireNonNull(id, "id must not be null");
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

}
