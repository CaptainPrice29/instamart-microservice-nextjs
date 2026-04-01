package com.instamart.email_service.schedular;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.instamart.email_service.service.EmailService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EmailSchedular {
    private final EmailService emailService;

    // every 1 minute
    @Scheduled(cron = "0/60 * * * * *")
    public void sendSimpleMailToAllUsers() {
        emailService.sendSimpleMailToAllUsers();
    }
}
