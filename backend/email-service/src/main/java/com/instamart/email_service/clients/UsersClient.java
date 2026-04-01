package com.instamart.email_service.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import com.instamart.email_service.commons.dto.UsersEmailDTO;

@FeignClient(name = "user-service", url = "http://localhost:8082")
public interface UsersClient {
    @GetMapping("/api/users/users-email")
    List<UsersEmailDTO> getAllUsersEmail();
}
