package com.instamart.email_service.service;

import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.instamart.email_service.clients.UsersClient;
import com.instamart.email_service.commons.dto.UsersEmailDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final UsersClient usersClient;
    private final JavaMailSender javaMailSender;
    private static final String FROM_EMAIL = "captainprice2905@gmail.com";

    public List<UsersEmailDTO> getAllUsersEmail() {
        return usersClient.getAllUsersEmail();
    }

    public void sendSimpleMailToAllUsers() {
        List<UsersEmailDTO> users = getAllUsersEmail();
        for (UsersEmailDTO user : users) {
            sendSimpleMail(user.getEmail(), "Welcome to Instamart", "Thank you for registering with Instamart");
        }
    }

    private void sendSimpleMail(String recipient, String subject, String msgBody) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(FROM_EMAIL);
            mailMessage.setTo(recipient);
            mailMessage.setSubject(subject);
            mailMessage.setText(msgBody);
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
