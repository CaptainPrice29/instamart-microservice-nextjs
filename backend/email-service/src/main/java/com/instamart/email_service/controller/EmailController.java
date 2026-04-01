package com.instamart.email_service.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.instamart.email_service.commons.dto.UsersEmailDTO;
import com.instamart.email_service.service.EmailService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/emails")
public class EmailController {
    private final EmailService emailService;

    @GetMapping
    public ResponseEntity<List<UsersEmailDTO>> getAllUsersEmail() {
        return ResponseEntity.ok(emailService.getAllUsersEmail());
    }

    @GetMapping("/send-simple-mail-to-all-users")
    public ResponseEntity<String> sendSimpleMailToAllUsers() {
        emailService.sendSimpleMailToAllUsers();
        return ResponseEntity.ok("Simple mail sent to all users");
    }
}
