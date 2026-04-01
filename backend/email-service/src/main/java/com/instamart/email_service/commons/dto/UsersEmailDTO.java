package com.instamart.email_service.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersEmailDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;

}
